package com.example.todoboom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    var myCoordinatorLayout: CoordinatorLayout = findViewById(R.id.my_coordinator_layout)

    lateinit var diceImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val createButton: Button = findViewById(R.id.create_button)
        diceImage = findViewById(R.id.dice_image)
        createButton.setOnClickListener { addToDo() }


    }

    private fun addToDo() {
//        Toast.makeText(this, getString(R.string.todo_creation_toast), Toast.LENGTH_SHORT).show()
        val randomInt = (1..6).random()
        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        dice_image.setImageResource(drawableResource)
    }
}
