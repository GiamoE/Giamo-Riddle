package com.example.riddlemethis.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.riddlemethis.R
import com.example.riddlemethis.adapter.RiddleAdapter
import com.example.riddlemethis.database.RiddleRepository
import com.example.riddlemethis.model.Riddle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.riddle_content_main.*
import kotlinx.android.synthetic.main.riddle_item.*
import kotlinx.android.synthetic.main.riddle_item.view.*
import kotlinx.android.synthetic.main.riddles_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RiddlesActivity : AppCompatActivity() {

    // variables
    private var riddlesList = arrayListOf<Riddle>()
    private var riddleAdapter =
        RiddleAdapter(riddlesList)
    private lateinit var riddleRepository: RiddleRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.riddles_main)

        riddleRepository = RiddleRepository(this)
        initViews()
    }

    // here we position the items within the recyclerview by using a layout.
    // we also add some decoration so the items have a small "gap"
    private fun initViews() {
        rvRiddles.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvRiddles.adapter = riddleAdapter
        rvRiddles.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        // Enable swipe behavior
        createItemTouchHelper().attachToRecyclerView(rvRiddles)
        getRiddleListFromDatabase()

        fab.setOnClickListener { startAddActivity() }
    }

    private fun startAddActivity(){
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    private fun startUpdateActivity() {
        val intent = Intent(this, UpdateActivity::class.java)
        startActivity(intent)
    }


    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val answer = viewHolder.itemView.tvAnswer
                val userAnswer = viewHolder.itemView.etUserAnswer
                val updateButton = viewHolder.itemView.fabItemUpdate

                // on left swipe check for the answer
                if (direction == ItemTouchHelper.LEFT) {
                    checkAnswer(userAnswer, answer)
                    riddleAdapter.notifyItemChanged(viewHolder.adapterPosition)
                }

                // on right swipe show the answer
                if (direction == ItemTouchHelper.RIGHT) {
                    showAnswer(answer)
                    updateButton.setOnClickListener { startUpdateActivity() }
                }
                riddleAdapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }
        return ItemTouchHelper(callback)
    }

    // delete * from riddles table.
    // use the Coroutine pattern to handle potential long running tasks in asynch
    // the Dispatchers.Main makes it run on a main thread
    // while the Dispatchers.IO is used for disk / network processes
    // use withContext to run it in a thread pool
    private fun deleteRiddlesList() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                riddleRepository.deleteAllRidles()
            }

            getRiddleListFromDatabase()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. Like the delete all riddles option
        return when (item.itemId) {
            R.id.action_delete_shopping_list -> {
                deleteRiddlesList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getRiddleListFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val riddlesList = withContext(Dispatchers.IO) {
                riddleRepository.getAllRiddles()
            }
            this@RiddlesActivity.riddlesList.clear()
            this@RiddlesActivity.riddlesList.addAll(riddlesList)
            this@RiddlesActivity.riddleAdapter.notifyDataSetChanged()
        }
    }

    // visibility is standard invisible, set it to visible when called.
    private fun showAnswer(answer: TextView) {
        answer.visibility = View.VISIBLE
    }

    // compare the two text view's text values.
    private fun checkAnswer(userAnswer: TextView, answer: TextView) {
        if (userAnswer.text.toString() == answer.text.toString()) {
            Snackbar.make(tvSnackbar, "Correct!", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(tvSnackbar, "Incorrect!", Snackbar.LENGTH_SHORT).show()
        }
    }
}
