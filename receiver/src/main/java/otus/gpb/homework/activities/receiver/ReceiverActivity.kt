package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val posterImageView = findViewById<ImageView>(R.id.posterImageView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)

        intent.let {
            val title = it.getStringExtra("title")
            titleTextView.text = title.toString()
            val year = it.getStringExtra("year")
            yearTextView.text = year.toString()
            val description = it.getStringExtra("description")
            descriptionTextView.text = description.toString()
            var image = getDrawable(R.drawable.interstellar)
            if (title == "Славные парни") {
                image = getDrawable(R.drawable.niceguys)
            }
            posterImageView.setImageDrawable(image)
        }
    }
}