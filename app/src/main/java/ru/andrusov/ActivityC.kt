package ru.andrusov

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.R

class ActivityC : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.button_c_to_a).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)

            startActivity(intent)
        }

        findViewById<Button>(R.id.button_c_to_d).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY

            startActivity(intent)
        }

        findViewById<Button>(R.id.close_activity_c).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.close_stack).setOnClickListener {
            finishAffinity()
        }
    }
}
