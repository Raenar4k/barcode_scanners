package com.example.zxing

import android.graphics.ImageFormat
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.view.CameraView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var cameraView: CameraView
    private lateinit var fotoapparat: Fotoapparat
    private lateinit var detector: BarcodeDetector
    private var rawValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.result)
        cameraView = findViewById(R.id.camera_view)

        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,                   // view which will draw the camera preview
                scaleType = ScaleType.CenterCrop,    // (optional) we want the preview to fill the view
                lensPosition = back(),               // (optional) we want back camera
                cameraConfiguration = CameraConfiguration(
                        frameProcessor = {
                            val frame = Frame
                                    .Builder()
                                    .setImageData(
                                            ByteBuffer.wrap(it.image),
                                            it.size.width,
                                            it.size.height,
                                            ImageFormat.NV21)
                                    .build()
                            val barcodes = detector.detect(frame)
                            if (barcodes != null && barcodes.size() != 0) {
                                rawValue = barcodes.valueAt(0)?.rawValue ?: ""
                                print(barcodes.toString())
                                print(barcodes[0])
                            }
                        }
                ),
                cameraErrorCallback = { error -> }   // (optional) log fatal errors
        )

        detector = BarcodeDetector.Builder(applicationContext)
                .setBarcodeFormats(Barcode.DATA_MATRIX or
                        Barcode.QR_CODE or
                        Barcode.EAN_13 or
                        Barcode.EAN_8 or
                        Barcode.CODE_128)
                .build()
        if (!detector.isOperational) {
            textView.text = "Could not set up the detector!"
            return
        }
    }

    override fun onStart() {
        super.onStart()
        fotoapparat.start()
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { textView.text = rawValue }
    }

    override fun onStop() {
        super.onStop()
        fotoapparat.stop()
    }

}
