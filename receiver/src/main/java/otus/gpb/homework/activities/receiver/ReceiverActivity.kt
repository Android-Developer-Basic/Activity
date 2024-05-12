package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

const val PLD_TITLE = "title"
const val PLD_YEAR = "year"
const val PLD_DESCR = "description"

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title: String = intent.extras?.getString(PLD_TITLE) ?: "Unknown title"
        val year: String = intent.extras?.getString(PLD_YEAR) ?: "Unknown year"
        val descr: String = intent.extras?.getString(PLD_DESCR) ?: "Unknown description"

        val drawableId: Int = when (title.lowercase()) {
            "interstellar" -> R.drawable.interstellar
            "nice guys" -> R.drawable.niceguys
            "terminator 2" -> R.drawable.terminator2
            else -> -1
        }
        val drawable: Drawable? = if (drawableId < 0) null else ContextCompat.getDrawable(this, drawableId)

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = descr
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(drawable)
    }
}