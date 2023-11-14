package com.example.receivermodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity(R.layout.activity_receiver) {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_receiver)
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // получаем extras
        val extras = intent.extras
        if (extras != null) {

            val title = extras.getString("title")
            val year = extras.getString("year")
            val description = extras.getString("description")

            // отображаем данные
            findViewById<TextView>(R.id.titleTextView).text = title
            findViewById<TextView>(R.id.yearTextView).text = year
            findViewById<TextView>(R.id.descriptionTextView).text  = description

            // отображаем картинку
            when(title) {
                "Гарри Поттер" -> findViewById<ImageView>(R.id.posterImageView).setImageResource(R.drawable.ic_harrypotter_background)

            }
        }
    }

}