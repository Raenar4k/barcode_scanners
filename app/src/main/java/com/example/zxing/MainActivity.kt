package com.example.zxing

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.view.CameraView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var cameraView: CameraView
    private lateinit var fotoapparat: Fotoapparat

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
                }
        ),
                cameraErrorCallback = { error -> }   // (optional) log fatal errors
        )
    }

    override fun onStart() {
        super.onStart()
        fotoapparat.start()
    }

    override fun onStop() {
        super.onStop()
        fotoapparat.stop()
    }

}
