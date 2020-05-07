package com.example.todoboom.todoadder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoboom.convertLongToDateString
import com.example.todoboom.database.TodoItem
import com.example.todoboom.databinding.ListItemTodoBinding

class TodoItemAdapter() :
    ListAdapter<TodoItem, TodoItemAdapter.ViewHolder>(TodoItemDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: TodoItem
        ) {
            val res = itemView.context.resources
            binding.todoDesc.text = item.todoDesc
            binding.startTime.text = convertLongToDateString(item.startTimeMilli)
            binding.endTime.text = convertLongToDateString(item.endTimeMilli)
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
