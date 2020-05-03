package com.example.todoboom.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDatabaseDao {
    @Insert
    fun add(todo: TodoItem)

    @Update
    fun update(todo: TodoItem)

    @Query("SELECT * from todo_item_table WHERE todoId= :key")
    fun get(key: Long): TodoItem?

    @Query("DELETE from todo_item_table")
    fun clear()

    @Query("SELECT * from todo_item_table ORDER BY todoId DESC LIMIT 1")
    fun getNewest(): TodoItem?

    @Query("SELECT * from todo_item_table ORDER BY todoId DESC")
    fun getAllTodos(): LiveData<List<TodoItem>>
}