package ru.andrusov

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.R

class ActivityA : AppCompatActivity() {

    private val TAG: String = "ActivityA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_a)

        findViewById<Button>(R.id.button_a_to_b).setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            startActivity(intent)
        }
    }
}
