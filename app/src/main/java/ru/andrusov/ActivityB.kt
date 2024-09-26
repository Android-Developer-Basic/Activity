package ru.andrusov

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.R

class ActivityB: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        val button = findViewById<Button>(R.id.button_b_to_c)
        button.setOnClickListener {
            startActivity(Intent(this, ActivityC::class.java))
        }
    }
}
