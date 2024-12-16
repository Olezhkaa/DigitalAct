package com.example.digitalact.Presentation.UI.Activity

import android.Manifest

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.digitalact.R
import com.example.digitalact.databinding.ActivityScannerBinding
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class ScannerActivity : AppCompatActivity() {

    private lateinit var barcodeView: DecoratedBarcodeView
    private var isFlashOn = false

    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barcodeView = findViewById(R.id.barcode_scanner)
        val flashlightButton = findViewById<ImageButton>(R.id.flashlight_button)

        // Проверка разрешения на использование камеры
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Если разрешение уже предоставлено, запускаем сканер
            barcodeView.resume()
        } else {
            // Если разрешение не предоставлено, запрашиваем его
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }

        // Настройка сканера штрих-кодов
        barcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                // Обработка результата сканирования
                result?.text?.let {
                    // Здесь можно обработать сканированный результат
                    barcodeView.pause()  // Пауза после сканирования

                    val resultIntent = Intent()
                    resultIntent.putExtra("SCAN_RESULT", it)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }

            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
        })

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == 100) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Разрешение предоставлено, запускаем сканер
                    barcodeView.resume()
                } else {
                }
            }
        }

        // Включение и выключение фонарика по нажатию кнопки
        flashlightButton.setOnClickListener {
            if(!isFlashOn) {
                barcodeView.setTorchOn()
                isFlashOn = true
            }
            else {
                barcodeView.setTorchOff()
                isFlashOn = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }
}

