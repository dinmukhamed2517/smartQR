package com.example.qrattendance.screens.student

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.qrattendance.base.BaseFragment
import com.example.qrattendance.databinding.FragmentQrScannerBinding
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QrScannerFragment : BaseFragment<FragmentQrScannerBinding>(FragmentQrScannerBinding::inflate) {

    private val cameraPermissionCode = 101

    override fun onBindView() {
        super.onBindView()

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                cameraPermissionCode
            )
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
        binding.barcodeScanner.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.text?.let {
                    Log.d("QrScanner", "QR Code Scanned: $it")
                    binding.barcodeScanner.pause()
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })
        binding.barcodeScanner.resume()
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.pause()
    }
}
