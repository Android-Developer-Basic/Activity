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

        val titleView: TextView = findViewById(R.id.titleTextView)
        val yearView: TextView = findViewById(R.id.yearTextView)
        val descriptionView: TextView = findViewById(R.id.descriptionTextView)
        val posterView: ImageView = findViewById(R.id.posterImageView)

        val movieTitle = intent.getStringExtra("title") ?: ""
        val movieYear = intent.getStringExtra("year") ?: ""
        val movieDescription = intent.getStringExtra("description") ?: ""

        titleView.text = movieTitle
        yearView.text = movieYear
        descriptionView.text = movieDescription

        val posterDrawable: Drawable? = when (movieTitle) {
            "niceguys" -> getDrawable(R.drawable.niceguys)
            "interstellar" -> getDrawable(R.drawable.interstellar)
            else -> null
        }

        posterDrawable?.let {
            posterView.setImageDrawable(it)
        }
    }
}
