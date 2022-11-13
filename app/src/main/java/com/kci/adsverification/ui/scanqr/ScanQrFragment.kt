package com.kci.adsverification.ui.scanqr

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kci.adsverification.R
import com.budiyev.android.codescanner.*
import com.kci.adsverification.data.model.IklanResponse
import com.kci.adsverification.databinding.FragmentScanQrBinding
import com.kci.adsverification.ui.dataiklan.DataIklanFragment.Companion.ID_IKLAN_KEY
import org.json.JSONObject

class ScanQrFragment : Fragment() {

    private var _binding: FragmentScanQrBinding? = null
    private val binding get() = _binding!!
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScanQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeScanner = CodeScanner(requireContext(), binding.scanner)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {
//                    AlertDialog.Builder(requireContext())
//                        .setTitle("Hasil")
//                        .setMessage(it.text)
//                        .show()
                    try {
//                        val jsonObject = JSONObject(it.text)
                        val bundle = Bundle()
                        bundle.putInt(ID_IKLAN_KEY, it.text.toInt())
//                        bundle.putString(NO_KERETA_KEY, jsonObject.getString(NO_KERETA_KEY))
//                        bundle.putString(NO_RANGKAIAN_KEY, jsonObject.getString(NO_RANGKAIAN_KEY))
//                        bundle.putString(NAMA_AGENCY_KEY, jsonObject.getString(NAMA_AGENCY_KEY))
//                        bundle.putString(NO_KONTRAK_KEY, jsonObject.getString(NO_KONTRAK_KEY))
//                        bundle.putString(TITIK_IKLAN_KEY, jsonObject.getString(TITIK_IKLAN_KEY))
//                        bundle.putString(KONTEN_IKLAN_KEY, jsonObject.getString(KONTEN_IKLAN_KEY))
//                        bundle.putString(DURASI_AWAL_KEY, jsonObject.getString(DURASI_AWAL_KEY))
//                        bundle.putString(DURASI_AKHIR_KEY, jsonObject.getString(DURASI_AKHIR_KEY))
//                        bundle.putParcelable("data_iklan", IklanResponse(it.))
                        findNavController().navigate(R.id.action_scanQrFragment_to_dataIklanFragment, bundle)
                    } catch (e: Exception){
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage("QR Salah!")
                            .setPositiveButton("Tutup"){btn,_ ->
                                btn.dismiss()
                            }
                            .show()
                    }
//                    AlertDialog.Builder(requireContext())
//                            .setTitle("ID IKLAN")
//                            .setMessage(it.text)
//                            .setPositiveButton("Tutup"){btn,_ ->
//                                btn.dismiss()
//                            }
//                            .show()
                }
            }
            errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage(it.message)
                        .setCancelable(false)
                        .show()
                }
            }

            binding.scanner.setOnClickListener {
                codeScanner.startPreview()
            }

        }
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}