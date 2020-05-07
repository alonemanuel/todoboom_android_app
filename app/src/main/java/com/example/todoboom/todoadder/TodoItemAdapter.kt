package com.example.todoboom.todoadder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoboom.convertLongToDateString
import com.example.todoboom.database.TodoItem
import com.example.todoboom.databinding.ListItemTodoBinding

class TodoItemAdapter(val clickListener: TodoItemListener) :
    ListAdapter<TodoItem, TodoItemAdapter.ViewHolder>(TodoItemDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: TodoItem,
            clickListener: TodoItemListener
        ) {
            val res = itemView.context.resources
            binding.todo = item
            binding.todoDesc.text = item.todoDesc
            binding.startTime.text = convertLongToDateString(item.startTimeMilli)
            binding.endTime.text = convertLongToDateString(item.endTimeMilli)
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

class TodoItemListener(val clickListener: (todoDesc: String) -> Unit) {


    fun onClick(todo: TodoItem) = clickListener(todo.todoDesc)
}
