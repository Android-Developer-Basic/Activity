package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val KEY_TITLE = "title"
private const val KEY_DESCRIPTION = "description"
private const val KEY_YEAR = "year"
private const val NICE_GUYS = "Хорошие парни"
private const val INTERSTELLAR = "Интерстеллар"

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.extras?.getString(KEY_TITLE)

        findViewById<TextView>(R.id.titleTextView).text = title

        findViewById<TextView>(R.id.descriptionTextView).text =
            intent.extras?.getString(KEY_DESCRIPTION)

        findViewById<TextView>(R.id.yearTextView).text = intent.extras?.getString(KEY_YEAR)

        val posterImageView: ImageView = findViewById(R.id.posterImageView)

        when (title) {
            NICE_GUYS -> posterImageView.setImageResource(R.drawable.niceguys)
            INTERSTELLAR -> posterImageView.setImageResource(R.drawable.interstellar)
        }
    }
}