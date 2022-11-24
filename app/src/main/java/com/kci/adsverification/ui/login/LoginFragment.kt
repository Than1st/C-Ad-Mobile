package com.kci.adsverification.ui.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kci.adsverification.R
import com.kci.adsverification.data.Status
import com.kci.adsverification.data.datastore.UserPreferences.Companion.DEF_NIK
import com.kci.adsverification.data.model.LoginRequest
import com.kci.adsverification.databinding.FragmentLoginBinding
import com.kci.adsverification.ui.dashboard.DashboardFragment.Companion.NAMA_VERIFIKATOR
import com.kci.adsverification.ui.dashboard.DashboardFragment.Companion.NIK_VERIFIKATOR
import com.kci.adsverification.ui.dashboard.DashboardFragment.Companion.TOKEN_VERIFIKATOR
import com.kci.adsverification.ui.dashboard.DashboardFragment.Companion.USERNAME_VERIFIKATOR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserPref()
        binding.btnLogin.setOnClickListener {
            if (binding.etNip.text.toString() == "" || binding.etPassword.text.toString() == "") {
                AlertDialog.Builder(requireContext())
                    .setTitle("")
                    .setMessage("Username atau Password tidak boleh kosong")
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                val requestLogin =
                    LoginRequest(binding.etNip.text.toString(), binding.etPassword.text.toString())
                viewModel.authLogin(requestLogin)
            }
        }
        val progressDialog = ProgressDialog(requireContext())
        viewModel.login.observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("Please Wait...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    if (resources.data != null) {
                        if (resources.data.user.aktif == "1") {
                            viewModel.setUserPref(
                                resources.data.user.nik.toString(),
                                resources.data.user.name,
                                resources.data.user.username,
                                resources.data.accessToken
                            )
                            Handler().postDelayed({
                                viewModel.getUserPref()
                                progressDialog.dismiss()
                            }, 1000)
                        } else {
                            progressDialog.dismiss()
                            AlertDialog.Builder(requireContext())
                                .setTitle("Akun Tidak Aktif!")
                                .setMessage("Silahkan Hubungi Admin C-Ad untuk mengaktifkan akun")
                                .setPositiveButton("Ok") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Username/Password Salah!", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                }
            }
        }
        viewModel.alreadyLogin.observe(viewLifecycleOwner) { res ->
            if (res.nik != DEF_NIK) {
                bundle.putString(NIK_VERIFIKATOR, res.nik)
                bundle.putString(NAMA_VERIFIKATOR, res.name)
                bundle.putString(USERNAME_VERIFIKATOR, res.username)
                bundle.putString(TOKEN_VERIFIKATOR, res.accessToken)
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment, bundle)
            }
        }
    }
}