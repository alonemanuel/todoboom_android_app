package com.example.todoboom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val createButton: Button = findViewById(R.id.create_button)
        createButton.setOnClickListener { addToDo() }
    }

    private fun addToDo() {
        Toast.makeText(this, getString(R.string.todo_creation_toast), Toast.LENGTH_SHORT).show()
    }
}
