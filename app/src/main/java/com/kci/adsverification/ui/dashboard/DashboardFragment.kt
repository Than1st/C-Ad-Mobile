package com.kci.adsverification.ui.dashboard

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kci.adsverification.R
import com.kci.adsverification.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() =_binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private var token = "0"

    companion object{
        const val NIK_VERIFIKATOR = "nik_verifikator"
        const val NAMA_VERIFIKATOR = "nama_verifikator"
        const val USERNAME_VERIFIKATOR = "username_verifikator"
        const val TOKEN_VERIFIKATOR = "token_verifikator"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        val getBundle = arguments
        if(getBundle?.getString(NIK_VERIFIKATOR) != null){
            token = getBundle.getString(TOKEN_VERIFIKATOR)!!
            binding.etNameVerifikator.text = checkNull(getBundle.getString(NAMA_VERIFIKATOR))
            binding.etContentNik.text = getString(R.string.format_content, checkNull(getBundle.getString(NIK_VERIFIKATOR)))
            binding.etContentUsername.text = getString(R.string.format_content, checkNull(getBundle.getString(USERNAME_VERIFIKATOR)))
        }
        binding.btnScan.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_scanQrFragment)
        }
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Logout Akun?")
                .setPositiveButton("Ya") { dialog, _ ->
                    viewModel.logout("Bearer $token")
                    viewModel.deleteUserPref()
                    dialog.dismiss()
                    AlertDialog.Builder(requireContext())
                        .setMessage("Berhasil Logout!")
                        .setPositiveButton("Ok"){p,_->
                            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
                            p.dismiss()
                        }
                        .setCancelable(false)
                        .show()
                }
                .setNegativeButton("Tidak"){dialog1, _ ->
                    dialog1.dismiss()
                }
                .show()
        }
    }

    private fun checkNull(value: String?): String {
        return when {
            value.isNullOrEmpty() -> {
                ""
            }
            value == "null" -> {
                ""
            }
            else -> {
                value
            }
        }
    }

}