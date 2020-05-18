package com.example.todoboom.todoadder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoboom.convertLongToDateString
import com.example.todoboom.database.TodoItem
import com.example.todoboom.databinding.ListItemTodoBinding
import timber.log.Timber

class TodoItemAdapter(val clickListener: TodoItemListener) :
    ListAdapter<TodoItem, TodoItemAdapter.ViewHolder>(TodoItemDiffCallback()) {

//    var todoClickListener: TodoClickListener = TODO()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)

//        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
//            fun onLongClick(v: View): Boolean {
//                return false
//            }
//        })

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

        init {
            itemView.setOnLongClickListener(this)
        }

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

        override fun onLongClick(v: View?): Boolean {
            Timber.i("long clicked")
            Toast.makeText(v?.context, "long click", Toast.LENGTH_SHORT).show()
            return true
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

interface TodoClickListener {
    fun onTodoClicked(todo: TodoItem)
}

class TodoItemListener(
    val clickListener: (todo: TodoItem) -> Unit,
    val holdListener: (todo: TodoItem) -> Unit
) {


    fun onClick(todo: TodoItem) = clickListener(todo)

    fun onLongPress(todo: TodoItem) = holdListener(todo)

}

class TodoItemOnHoldListener(val holdListener: (todoId: Long) -> Unit) {
    fun onHold(todo: TodoItem) = holdListener(todo.todoId)

}
