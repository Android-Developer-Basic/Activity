package otus.gpb.homework.activities.receiver

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val posterImageView = findViewById<ImageView>(R.id.posterImageView)

        intent.extras?.let {
            titleTextView.text = it.getString("title", ":( no title")
            descriptionTextView.text = it.getString("description", ":( no description")
            yearTextView.text = it.getString("year", ":( no year")
            posterImageView.setImageDrawable(getDrawable(R.drawable.interstellar))
        }
    }
}