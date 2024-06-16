package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title: String = intent.getStringExtra(TITLE_KEY).toString()
        val year: String = intent.getStringExtra(YEAR_KEY).toString()
        val description: String = intent.getStringExtra(DESCRIPTION_KEY).toString()

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.niceguys
            )
        )
    }

    companion object {
        const val TITLE_KEY = "title key"
        const val YEAR_KEY = "year key"
        const val DESCRIPTION_KEY = "description key"
    }
}
