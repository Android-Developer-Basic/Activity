package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val titleExtra = intent.extras?.getString("title")
        val yearExtra = intent.extras?.getString("year")
        val descriptionExtra = intent.extras?.getString("description")

        val title = findViewById<TextView>(R.id.titleTextView)
        val year = findViewById<TextView>(R.id.yearTextView)
        val description = findViewById<TextView>(R.id.descriptionTextView)
        val poster = findViewById<ImageView>(R.id.posterImageView)

        title.text = titleExtra
        year.text = yearExtra
        description.text = descriptionExtra
        initPoster(titleExtra, poster)
    }

    private fun initPoster(title: String?, poster: ImageView) {
        when (title) {
            "Интерстеллар" -> poster.setImageDrawable(getDrawable(R.drawable.interstellar))
            "Славные парни" -> poster.setImageDrawable(getDrawable(R.drawable.niceguys))
            else -> poster.setImageDrawable(null)
        }
    }
}