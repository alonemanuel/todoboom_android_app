package com.example.todoboom.todoadder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoboom.R
import com.example.todoboom.convertLongToDateString
import com.example.todoboom.database.TodoItem

class TodoItemAdapter() :
    RecyclerView.Adapter<TodoItemAdapter.ViewHolder>() {

    var data = listOf<TodoItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.todo_desc)
        val start: TextView = itemView.findViewById(R.id.start_time)
        val end: TextView = itemView.findViewById(R.id.end_time)

        fun bind(
            item: TodoItem
        ) {
            val res = itemView.context.resources
            description.text = item.todoDesc
            start.text = convertLongToDateString(item.startTimeMilli)
            end.text = convertLongToDateString(item.endTimeMilli)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_item_todo, parent, false)
                return ViewHolder(view)
            }
        }
    }


}

class TodoItemDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.todoId == newItem.todoId
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }

}

class TodoItemListener(val clickListener: (todoId: Long) -> Unit) {


    fun onClick(todo: TodoItem) = clickListener(todo.todoId)
}
