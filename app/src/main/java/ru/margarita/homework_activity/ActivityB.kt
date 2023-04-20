package ru.margarita.homework_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.R

class ActivityB:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        val button = findViewById<Button>(R.id.button)

        //2. По клику на кнопку “Open ActivityC” запустите ActivityC в том же стеке, в котором расположена ActivityB
        button.setOnClickListener{
            val intent = Intent (this, ActivityC::class.java)
            startActivity(intent)

        }

    }
}