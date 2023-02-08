@file:Suppress("DEPRECATION")

package com.kci.adsverification.ui.dataiklan

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kci.adsverification.R
import com.kci.adsverification.data.Status
import com.kci.adsverification.data.model.UpdateLaporanRequest
import com.kci.adsverification.databinding.FragmentDataIklanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataIklanFragment : Fragment() {

    companion object {
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
    private val viewModel: DataIklanViewModel by viewModels()
    private var accessToken = "default"
    private var nik = "0"

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
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            if (it.accessToken != "") {
                accessToken = it.accessToken
                nik = it.nik
                if (bundleIklan?.getInt(ID_IKLAN_KEY) != 0) {
                    bundleIklan?.getInt(ID_IKLAN_KEY)
                        ?.let { key -> viewModel.getIklan("Bearer $accessToken", key) }
                }
            }
        }

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Pesan")
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)
        viewModel.iklan.observe(viewLifecycleOwner) { IkRes ->
            when (IkRes.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("Please Wait...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    if (IkRes.data?.data?.iklan?.count() ?: 0 != 0) {
                        if(IkRes.data?.data?.iklan?.get(0)?.visible == true){
                        binding.apply {
                            etContentNamaAgency.text = getString(
                                R.string.format_content,
                                IkRes.data.data.iklan[0].namaAgensi
                            )
                            etContentNomorKontrak.text = getString(
                                R.string.format_content,
                                IkRes.data.data.iklan[0].noKontrak
                            )
                            etContentKontenIklan.text = getString(
                                R.string.format_content,
                                IkRes.data.data.iklan[0].kontenIklan
                            )
                            etContentDurasiAwal.text = getString(
                                R.string.format_content,
                                dateConvertion(
                                    IkRes.data.data.iklan[0].durasiAwal
                                )
                            )
                            etContentDurasiAkhir.text = getString(R.string.format_content,
                                dateConvertion(
                                    IkRes.data.data.iklan[0].durasiAkhir
                                )
                            )
                        }
                        var listKeretaIklan = ""
                        var listTitikIklan = ""
                        var listKategoriIklan = ""
                        var listTrainsetIklan = ""
                        val keretaIklan = IkRes.data.data.keretaIklan
                        val titikIklan = IkRes.data.data.titik
                        val kategoriIklan = IkRes.data.data.kategori
                        val trainsetIklan = IkRes.data.data.trainsetIklan
                        for (data in keretaIklan) {
                            listKeretaIklan += ", ${data.namaKereta}"
                        }
                        for (data in titikIklan) {
                            listTitikIklan += ", ${data.namaTitik}"
                        }
                        for (data in kategoriIklan) {
                            listKategoriIklan += ", ${data.namaKategori}"
                        }
                        for (data in trainsetIklan) {
                            listTrainsetIklan += ", ${data.namaTrainset}"
                        }
                        binding.etContentNamaKereta.text = listKeretaIklan.drop(2)
                        binding.etContentTitik.text = listTitikIklan.drop(2)
                        binding.etContentKategoriIklan.text = listKategoriIklan.drop(2)
                        binding.etContentNamaTrainset.text = listTrainsetIklan.drop(2)
                        } else {
                            findNavController().popBackStack()
                            Toast.makeText(requireContext(), "Iklan Tidak Ada!", Toast.LENGTH_SHORT)
                                .show()
                            progressDialog.dismiss()
                        }
                    } else {
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Iklan Tidak Ada!", Toast.LENGTH_SHORT)
                            .show()
                        progressDialog.dismiss()
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Gagal ambil data Iklan!", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    findNavController().popBackStack()
                }
            }
        }
        viewModel.responseUpdateLaporan.observe(viewLifecycleOwner){ ulr ->
            when (ulr.status) {
                Status.LOADING -> {
                    progressDialog.setMessage("Please Wait...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()
                }
                Status.SUCCESS -> {
                    progressDialog.dismiss()
                    if (ulr.data?.status == true){
                        findNavController().navigate(R.id.action_dataIklanFragment_to_alertPageFragment)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Gagal Kirim!", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                    findNavController().popBackStack()
                }
            }
        }
        binding.btnYa.setOnClickListener {
            alertDialog.setMessage("Anda memilih \"Ya\", apakah anda yakin?")
            alertDialog.setTitle("Konfirmasi")
            alertDialog.setPositiveButton("Yakin") { posBtn, _ ->
                posBtn.dismiss()
                var catatan = "-"
                if (binding.etCatatan.text.toString().isNotEmpty()){
                    catatan = binding.etCatatan.text.toString()
                }
                val dataIklan = UpdateLaporanRequest(
                    catatan,
                    "Sesuai",
                    nik,
                    1
                )
                bundleIklan?.getInt(ID_IKLAN_KEY)
                    ?.let { it1 -> viewModel.updateLaporan("Bearer $accessToken", it1, dataIklan)}
            }
            alertDialog.setNegativeButton("Tidak") { negBtn, _ ->
                negBtn.dismiss()
            }
            alertDialog.show()
        }
        binding.btnTidak.setOnClickListener {
            if (binding.etCatatan.text.toString() == "") {
                alertDialog.setMessage("Catatan tidak boleh kosong!")
                alertDialog.setPositiveButton("Oke") { btn, _ ->
                    btn.dismiss()
                }
                alertDialog.setCancelable(false)
                alertDialog.show()
            } else {
                alertDialog.setMessage("Anda memilih \"Tidak\", apakah anda yakin?")
                alertDialog.setTitle("Konfirmasi")
                alertDialog.setPositiveButton("Yakin") { posBtn, _ ->
                    posBtn.dismiss()
                    val dataIklan = UpdateLaporanRequest(
                        binding.etCatatan.text.toString(),
                        "Tidak Sesuai",
                        nik,
                        1
                    )
                    bundleIklan?.getInt(ID_IKLAN_KEY)
                        ?.let { it1 -> viewModel.updateLaporan("Bearer $accessToken", it1, dataIklan)}
                }
                alertDialog.setNegativeButton("Tidak") { negBtn, _ ->
                    negBtn.dismiss()
                }
                alertDialog.show()
            }
        }
    }

    private fun dateConvertion(date: String): String {
        var dummy = date
        val year = dummy.take(4)
        dummy = dummy.drop(5)
        val month = dummy.take(2)
        dummy = dummy.drop(3)
        val day = dummy.take(2)
        return "$day-$month-$year"
    }

}