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

        val title: String = intent.extras?.getString("title") ?: "Unknown movie"

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = intent.extras?.getString("year")
        findViewById<TextView>(R.id.descriptionTextView).text = intent.extras?.getString("description")

        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
            when (title) {
                "Интерстеллар" -> ContextCompat.getDrawable(this, R.drawable.interstellar)
                "Славные парни" -> ContextCompat.getDrawable(this, R.drawable.niceguys)
                else -> null
            }
        )
    }
}
