package com.example.todoboom.todoadder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoboom.R
import com.example.todoboom.TextItemViewHolder
import com.example.todoboom.database.TodoItem
import com.example.todoboom.databinding.ListItemTodoBinding

class TodoItemAdapter : RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<TodoItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.todoDesc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }


    class ViewHolder private constructor(val binding: ListItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TodoItem, clickListener: TodoItemListener) {
            binding.todo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTodoBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
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
