package com.example.riddlemethis.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.riddlemethis.R
import com.example.riddlemethis.activities.RiddlesActivity
import com.example.riddlemethis.activities.UpdateActivity
import com.example.riddlemethis.model.Riddle
import kotlinx.android.synthetic.main.riddle_item.view.*


class RiddleAdapter(private val riddles: List<Riddle>) :
    RecyclerView.Adapter<RiddleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.riddle_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return riddles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(riddles[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(riddle: Riddle) {
            itemView.tvName.text = riddle.name
            itemView.tvContent.text = riddle.description
            itemView.tvAnswer.text = riddle.answer

//            itemView.fabItemUpdate.setOnClickListener { startUpdateActivity() }
        }
    }

//    private lateinit var context: Context
//
//    private fun startUpdateActivity() {
//        context.startActivity(Intent(context, UpdateActivity::class.java))
//    }
}