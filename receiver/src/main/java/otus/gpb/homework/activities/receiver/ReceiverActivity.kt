package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.ImageView


class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.getStringExtra("title")
        val year = intent.getStringExtra("year")
        val description = intent.getStringExtra("description")

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val posterImageView = findViewById<ImageView>(R.id.posterImageView)

        titleTextView.text = title
        yearTextView.text = year
        descriptionTextView.text = description

        when (title) {
            "Славные парни" -> {
                posterImageView.setImageDrawable(getDrawable(R.drawable.niceguys))
            }
            "Интерстеллар" -> {
                posterImageView.setImageDrawable(getDrawable(R.drawable.interstellar))
            }
            else -> {
                posterImageView.setImageDrawable(null)
            }
        }
    }
}
