package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
const val TITLEKEY = "title"
const val YEARKEY = "year"
const val DESCRIPTIONKEY = "description"
class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val titleView: TextView = findViewById(R.id.titleTextView)
        val yearView: TextView = findViewById(R.id.yearTextView)
        val descriptionView: TextView = findViewById(R.id.descriptionTextView)
        val posterView: ImageView = findViewById(R.id.posterImageView)

        val title = getFromIntent(TITLEKEY)

        titleView.text = title
        yearView.text = getFromIntent(YEARKEY)
        descriptionView.text = getFromIntent(DESCRIPTIONKEY)
        posterView.setImageDrawable(getPoster(title))

    }

    private fun getFromIntent(key: String): String {
        return intent.getStringExtra(key) ?: ""
    }

    private fun getPoster(title: String): Drawable? {
        return when (title) {
            "Славные парни" -> getDrawable(R.drawable.niceguys)
            "Интерстеллар" -> getDrawable(R.drawable.interstellar)
            else -> null
        }
    }

}
