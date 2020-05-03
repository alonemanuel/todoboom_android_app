package com.example.todoboom.todoadder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.todoboom.database.TodoDatabaseDao

class AdderViewModel(val database: TodoDatabaseDao, application: Application) :
    AndroidViewModel(application) {
    
}