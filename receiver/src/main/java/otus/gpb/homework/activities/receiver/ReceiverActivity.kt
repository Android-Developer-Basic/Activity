package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val posterImageView = findViewById<ImageView>(R.id.posterImageView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)

        val title = intent.getStringExtra("title").orEmpty()
        val year = intent.getIntExtra("year", 0)
        val description = intent.getStringExtra("description").orEmpty()

        titleTextView.text = title
        yearTextView.text = year.toString()
        descriptionTextView.text = description

        /*
        val posterRes = when (title.lowercase()) {
            "inception"      -> R.drawable.poster_inception
            "interstellar"   -> R.drawable.poster_interstellar
            "matrix"         -> R.drawable.poster_matrix
            // добавьте остальные фильмы
            else             -> R.drawable.poster_default
        }
        posterImageView.setImageResource(posterRes)
         */
    }
}
