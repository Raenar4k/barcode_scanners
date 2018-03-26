package com.example.zxing

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

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

    }

}
