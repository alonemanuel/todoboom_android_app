package com.example.todoboom.todoadder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.todoboom.database.TodoDatabaseDao
import com.example.todoboom.database.TodoItem
import com.example.todoboom.formatTodos
import kotlinx.coroutines.*

class AdderViewModel(val database: TodoDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var newestTodo = MutableLiveData<TodoItem?>()
    private var todos = database.getAllTodos()
    val todosString = Transformations.map(todos) { todos ->
        formatTodos(todos, application.resources)
    }

    init {
        initializeTodo()
    }

    private fun initializeTodo() {
        uiScope.launch {
            newestTodo.value = getNewestTodoFromDatabase()
        }
    }

    private suspend fun getNewestTodoFromDatabase(): TodoItem? {
        return withContext(Dispatchers.IO) {
            var newestTodo = database.getNewest()
            if (newestTodo?.endTimeMilli != newestTodo?.startTimeMilli) {
                newestTodo = null
            }
            newestTodo
        }
    }

    fun onCreateTodo() {
        uiScope.launch {
            val newTodo = TodoItem()
            insert(newTodo)
            newestTodo.value = getNewestTodoFromDatabase()
        }
    }

    private suspend fun insert(newTodo: TodoItem) {
        withContext(Dispatchers.IO) {
            database.add(newTodo)

        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}