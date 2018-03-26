package com.example.zxing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.scan_button)
        textView = findViewById(R.id.result)
        button.setOnClickListener { onButtonClick() }
    }

    private fun onButtonClick() {
        val integrator = IntentIntegrator(this)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            IntentIntegrator.REQUEST_CODE -> {
                val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                textView.text = result.contents
            }

        }
    }

}
