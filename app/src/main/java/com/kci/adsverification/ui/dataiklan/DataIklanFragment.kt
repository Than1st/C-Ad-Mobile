package com.kci.adsverification.ui.dataiklan

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kci.adsverification.R
import com.kci.adsverification.data.Status
import com.kci.adsverification.databinding.FragmentDataIklanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataIklanFragment : Fragment() {

    companion object{
        const val ID_IKLAN_KEY = "id_iklan"
//        const val NO_KERETA_KEY = "nomor_kereta"
//        const val NO_RANGKAIAN_KEY = "nomor_rangkaian"
//        const val NAMA_AGENCY_KEY = "nama_agency"
//        const val NO_KONTRAK_KEY = "nomor_kontrak"
//        const val TITIK_IKLAN_KEY = "titik_iklan"
//        const val KONTEN_IKLAN_KEY = "konten_iklan"
//        const val DURASI_AWAL_KEY = "durasi_awal"
//        const val DURASI_AKHIR_KEY = "durasi_akhir"
    }

    private var _binding: FragmentDataIklanBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DataIklanViewModel by viewModels()
    private var accessToken = "default"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDataIklanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundleIklan = arguments
        viewModel.getUserPref()
        viewModel.userPreferences.observe(viewLifecycleOwner){
            if (it.accessToken != ""){
                accessToken = it.accessToken
                if (bundleIklan?.getInt(ID_IKLAN_KEY) != 0){
                    bundleIklan?.getInt(ID_IKLAN_KEY)?.let { key -> viewModel.getIklan("Bearer $accessToken", key) }
                }
            }
        }

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Pesan")
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)
        viewModel.iklan.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    progressDialog.setMessage("Please Wait...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    binding.apply {
                        etContentNamaAgency.text = getString(R.string.format_content, it.data?.data?.iklan?.get(0)?.namaAgensi ?: "nama_agensi")
                        etContentNamaTrainset.text = getString(R.string.format_content, it.data?.data?.iklan?.get(0)?.namaTrainset ?: "nama_trainset")
                        etContentNomorKontrak.text = getString(R.string.format_content, it.data?.data?.iklan?.get(0)?.noKontrak ?: "no_kontrak")
                        etContentKontenIklan.text = getString(R.string.format_content, it.data?.data?.iklan?.get(0)?.kontenIklan ?: "konten_iklan")
                        etContentKategoriIklan.text = getString(R.string.format_content, it.data?.data?.iklan?.get(0)?.kategoriIklan ?: "kategori_iklan")
                        etContentDurasiAwal.text = getString(R.string.format_content, it.data?.data?.iklan?.get(0)?.durasiAwal ?: "00-00-0000")
                        etContentDurasiAkhir.text = getString(R.string.format_content, it.data?.data?.iklan?.get(0)?.durasiAkhir ?: "00-00-0000")
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Ada Kesalahan", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    findNavController().popBackStack()
                }
            }
        }
//        binding.apply {
//            etContentNamaTrainset.text = getString(R.string.content_nomor_rangkaian, dataJson?.getString(NO_KERETA_KEY))
//            etContentNamaKereta.text = getString(R.string.content_nomor_kereta, dataJson?.getString(NO_RANGKAIAN_KEY))
//            etContentNamaAgency.text = getString(R.string.content_nomor_kereta, dataJson?.getString(NAMA_AGENCY_KEY))
//            etContentNomorKontrak.text = getString(R.string.content_nomor_kereta, dataJson?.getString(NO_KONTRAK_KEY))
//            etContentTitikIklan.text = getString(R.string.content_nomor_kereta, dataJson?.getString(TITIK_IKLAN_KEY))
//            etContentKontenIklan.text = getString(R.string.content_nomor_kereta, dataJson?.getString(KONTEN_IKLAN_KEY))
//            etContentDurasiAwal.text = getString(R.string.content_nomor_kereta, dataJson?.getString(DURASI_AWAL_KEY))
//            etContentDurasiAkhir.text = getString(R.string.content_nomor_kereta, dataJson?.getString(DURASI_AKHIR_KEY))
//        }
        binding.btnYa.setOnClickListener {
            findNavController().navigate(R.id.action_dataIklanFragment_to_alertPageFragment)
        }
        binding.btnTidak.setOnClickListener {
            if (binding.etCatatan.text.toString() == ""){
                alertDialog.setMessage("Catatan tidak boleh kosong!")
                alertDialog.setPositiveButton("Oke"){btn,_->
                    btn.dismiss()
                }
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else {
                findNavController().navigate(R.id.action_dataIklanFragment_to_alertPageFragment)
            }
        }
    }

}