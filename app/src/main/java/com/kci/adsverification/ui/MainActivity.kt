package com.kci.adsverification.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.kci.adsverification.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        private var CAMERA_PERMISSION_CODE: Int = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }
    private fun checkPermission(){
        when (PackageManager.PERMISSION_DENIED) {
            checkSelfPermission(Manifest.permission.CAMERA) -> {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                    AlertDialog.Builder(this)
                        .setTitle("Pesan")
                        .setMessage("Anda Harus Mengizinkan Akses Camera untuk menggunakan Aplikasi ini!")
                        .setPositiveButton("Keluar"){btn,_ ->
                            btn.dismiss()
                            finish()
                        }
                        .setCancelable(false)
                        .show()
                }
            }
        }
    }
}