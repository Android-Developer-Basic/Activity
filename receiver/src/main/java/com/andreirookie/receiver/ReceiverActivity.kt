package com.andreirookie.receiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ReceiverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val posterImageView = findViewById<ImageView>(R.id.PosterImageView)
        val titleTextView = findViewById<TextView>(R.id.title_textView)
        val yearTextView = findViewById<TextView>(R.id.year_textView)
        val descriptionTextView = findViewById<TextView>(R.id.description_textView)

        val intent = intent ?: return

        intent.let {
            if (intent.action != Intent.ACTION_SEND) {
                return@let
            }
//            val title1 = it.extras?.getString("title")
            val title = it.getStringExtra("title")
            titleTextView.text = title.toString()
            val year = it.getStringExtra("year")
            yearTextView.text = year.toString()
            val description = it.getStringExtra("description")
            descriptionTextView.text = description.toString()

            val image = if (title == "Славные парни") getDrawable(R.drawable.niceguys) else getDrawable(R.drawable.interstellar)
            posterImageView.setImageDrawable(image)
        }
    }
}