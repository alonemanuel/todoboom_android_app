package com.example.todoboom.todoadder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoboom.R
import com.example.todoboom.TextItemViewHolder
import com.example.todoboom.convertLongToDateString
import com.example.todoboom.database.TodoItem

class TodoItemAdapter : RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<TodoItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = convertLongToDateString(item.startTimeMilli)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }
}