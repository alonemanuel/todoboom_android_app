package com.example.todoboom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_item_table")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    var todoId: Long = 0L,
    @ColumnInfo(name = "todo_description")
    var todoDesc: String = "",
    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli// indicating the item hasn't ended yet
)