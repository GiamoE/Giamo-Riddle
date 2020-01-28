package com.example.riddlemethis.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.riddlemethis.R
import com.example.riddlemethis.adapter.RiddleAdapter
import com.example.riddlemethis.database.RiddleRepository
import com.example.riddlemethis.model.Riddle
import kotlinx.android.synthetic.main.add_riddle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddActivity : AppCompatActivity() {

    // variables
    private var riddlesList = arrayListOf<Riddle>()
    private var riddleAdapter =
        RiddleAdapter(riddlesList)
    private lateinit var riddleRepository: RiddleRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_riddle)

        riddleRepository = RiddleRepository(this)
        initViews()
    }

    private fun initViews() {
        getRiddleListFromDatabase()
        fabAdd.setOnClickListener { addRiddle() }
    }

    // first check if the fields are filled in.
    // after this create a riddle Object and insert this to add
    private fun addRiddle() {
        if (validateFields()) {
            CoroutineScope(Dispatchers.Main).launch {
                val riddle = Riddle(
                    name = etRiddleName.text.toString(),
                    description = etRiddleContent.text.toString(),
                    answer = etRiddleAnswer.text.toString()
                )
                // gebruik van IO voor disk & netwerk
                withContext(Dispatchers.IO) {
                    riddleRepository.insertRiddle(riddle)
                }

                getRiddleListFromDatabase()
            }
        }
        startRiddlesActivity()
    }

    private fun validateFields(): Boolean {
        return if (etRiddleName.text.toString().isNotBlank() && etRiddleContent.text.toString().isNotBlank() && etRiddleAnswer.text.toString().isNotBlank()) {
            true
        } else {
            Toast.makeText(this, R.string.fill_fields, Toast.LENGTH_SHORT).show()
            false
        }
    }


    private fun getRiddleListFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val riddlesList = withContext(Dispatchers.IO) {
                riddleRepository.getAllRiddles()
            }
            this@AddActivity.riddlesList.clear()
            this@AddActivity.riddlesList.addAll(riddlesList)
            this@AddActivity.riddleAdapter.notifyDataSetChanged()
        }
    }

    private fun startRiddlesActivity(){
        val intent = Intent(this, RiddlesActivity::class.java)
        startActivity(intent)
    }
}
