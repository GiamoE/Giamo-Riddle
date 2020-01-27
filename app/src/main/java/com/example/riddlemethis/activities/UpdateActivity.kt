package com.example.riddlemethis.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.riddlemethis.R
import com.example.riddlemethis.adapter.RiddleAdapter
import com.example.riddlemethis.database.RiddleRepository
import com.example.riddlemethis.model.Riddle
import kotlinx.android.synthetic.main.update_riddle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UpdateActivity : AppCompatActivity() {

    // variables
    private var riddlesList = arrayListOf<Riddle>()
    private var riddleAdapter =
        RiddleAdapter(riddlesList)
    private lateinit var riddleRepository: RiddleRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_riddle)

        riddleRepository = RiddleRepository(this)
        initViews()
    }

    private fun initViews() {
        getRiddleListFromDatabase()
        fabUpdate.setOnClickListener { updateRiddle() }
    }

    private fun updateRiddle() {
        if (validateFields()) {
            CoroutineScope(Dispatchers.Main).launch {
                val riddle = Riddle(
                    name = etRiddleNameUpdate.text.toString(),
                    description = etRiddleContentUpdate.text.toString(),
                    answer = etRiddleAnswerUpdate.text.toString()
                )
                // gebruik van IO voor disk & netwerk
                withContext(Dispatchers.IO) {
                    riddleRepository.updateRiddle(riddle)
                }

                getRiddleListFromDatabase()
            }
        }
        startRiddlesActivity()
    }

    private fun validateFields(): Boolean {
        return if (etRiddleNameUpdate.text.toString().isNotBlank() && etRiddleContentUpdate.text.toString().isNotBlank() && etRiddleAnswerUpdate.text.toString().isNotBlank()) {
            true
        } else {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show()
            false
        }
    }


    private fun getRiddleListFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val riddlesList = withContext(Dispatchers.IO) {
                riddleRepository.getAllProducts()
            }
            this@UpdateActivity.riddlesList.clear()
            this@UpdateActivity.riddlesList.addAll(riddlesList)
            this@UpdateActivity.riddleAdapter.notifyDataSetChanged()
        }
    }

    private fun startRiddlesActivity(){
        val intent = Intent(this, RiddlesActivity::class.java)
        startActivity(intent)
    }
}
