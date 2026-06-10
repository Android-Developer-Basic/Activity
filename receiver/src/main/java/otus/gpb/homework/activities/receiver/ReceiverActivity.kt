package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.getStringExtra(EXTRA_TITLE).orEmpty()
        val year = intent.getStringExtra(EXTRA_YEAR).orEmpty()
        val description = intent.getStringExtra(EXTRA_DESCRIPTION).orEmpty()

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description

        val poster = when (title) {
            "Славные парни" -> R.drawable.niceguys
            "Интерстеллар" -> R.drawable.interstellar
            else -> null
        }
        poster?.let {
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(it))
        }
    }

    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_YEAR = "year"
        private const val EXTRA_DESCRIPTION = "description"
    }
}
