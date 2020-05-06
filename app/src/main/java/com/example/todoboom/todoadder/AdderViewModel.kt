package com.example.todoboom.todoadder

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.todoboom.database.TodoDatabaseDao
import com.example.todoboom.database.TodoItem
import com.example.todoboom.formatTodos
import kotlinx.coroutines.*
import timber.log.Timber

class AdderViewModel(val database: TodoDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    val todoDescription = MutableLiveData<String>()


    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var newestTodo = MutableLiveData<TodoItem?>()
    val todos = database.getAllTodos()


    //    val todossString = "yo"
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

    fun onCreateTodo(todoInput: String) {
        // Todo: use binding
        Timber.i("in onCreateTodo in VM")


        uiScope.launch {
            val newTodo = TodoItem()
            newTodo.todoDesc = todoInput
            // todo: add the toast for when it's empty
//            newTodo.todoDesc =
            insert(newTodo)
            newestTodo.value = getNewestTodoFromDatabase()
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            newestTodo.value = null

        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
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

    fun onTodoItemClicked(id: Long) {


    }

}
