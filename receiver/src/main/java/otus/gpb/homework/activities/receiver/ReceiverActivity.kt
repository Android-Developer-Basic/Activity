package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        intent.extras?.let {
            findViewById<TextView>(R.id.titleTextView).text = it.getString("payloadTitle")
            findViewById<TextView>(R.id.descriptionTextView).text = it.getString("payloadDescription")
            findViewById<TextView>(R.id.yearTextView).text = it.getString("payloadYear")

            setImage(it.getString("payloadTitle")!!)
        }
    }

    private fun setImage(movieTitle: String) {
        when (movieTitle) {
            "Славные парни" -> {
                findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.niceguys))
            }
            "Интерстеллар" -> {
                findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.interstellar))
            }
            else -> { /* do nothing */ }
        }
    }
}