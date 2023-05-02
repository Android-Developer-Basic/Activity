package ru.margarita.homework_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.R

class ActivityA:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            val intent = Intent(this,ActivityB::class.java)
            startActivity(intent)
        }
    }
}