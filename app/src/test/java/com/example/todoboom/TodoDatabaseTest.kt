package com.example.todoboom

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.todoboom.database.TodoDatabase
import com.example.todoboom.database.TodoDatabaseDao
import com.example.todoboom.database.TodoItem
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit38ClassRunner
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)

class TodoDatabaseTest {

    private lateinit var todoDao: TodoDatabaseDao
    private lateinit var db: TodoDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        todoDao = db.todoDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTodo() {
        val todo = TodoItem()
        todoDao.add(todo)
        val newestTodo = todoDao.getNewest()
        Assert.assertEquals(newestTodo?.endTimeMilli, newestTodo?.startTimeMilli)
    }
}



