package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val TITLE_TAG = "title"
const val DESCRIPTION_TAG = "desc"
const val YEAR_TAG = "year"

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        fillViews()
    }

    private fun fillViews() {
        findViewById<TextView>(R.id.titleTextView).text = intent.getStringExtra(TITLE_TAG)
        findViewById<TextView>(R.id.descriptionTextView).text =
            intent.getStringExtra(DESCRIPTION_TAG)
        findViewById<TextView>(R.id.yearTextView).text = intent.getStringExtra(YEAR_TAG)
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
            getDrawable(
                if (intent.getStringExtra(TITLE_TAG) == "Интерстеллар")
                    R.drawable.interstellar
                else
                    R.drawable.niceguys
            )
        )
    }
}