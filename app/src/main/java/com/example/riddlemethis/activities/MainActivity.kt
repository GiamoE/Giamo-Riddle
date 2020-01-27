package com.example.riddlemethis.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.riddlemethis.R
import com.example.riddlemethis.model.Day

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initViews()

        btnHome.setOnClickListener {
            startRiddlesActivity()
        }
    }

    private fun startRiddlesActivity(){
        val intent = Intent(this, RiddlesActivity::class.java)
        startActivity(intent)
    }

    private fun initViews() {
        btnDay.setOnClickListener {
            viewModel.getCurrentDay()
        }
    }

    private fun initViewModel() {
        // Initialize the MainActivityViewModel.
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // Observe the day object.
        viewModel.day.observe(this, Observer {
            println("ValueDAY is: $it?.text")
            tvHome.text = it?.dayOfTheWeak
            println("ValueDAYX is: $it?.text")
        })

        // Observe the error message.
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }
}
