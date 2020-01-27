package com.example.riddlemethis.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.riddlemethis.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnHome.setOnClickListener {
            startRiddlesActivity()
        }
    }

    private fun startRiddlesActivity(){
        val intent = Intent(this, RiddlesActivity::class.java)
        startActivity(intent)
    }
}
