package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        intent.extras?.getString("title")?.let {
            findViewById<TextView>(R.id.titleTextView).text = it
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
                when (it) {
                    "Интерстеллар" -> AppCompatResources.getDrawable(this, R.drawable.interstellar)
                    "Славные парни" -> AppCompatResources.getDrawable(this, R.drawable.niceguys)
                    else -> null
                }
            )
        }

        intent.extras?.getString("year")?.let {
            findViewById<TextView>(R.id.yearTextView).text = it
        }

        intent.extras?.getString("description")?.let {
            findViewById<TextView>(R.id.descriptionTextView).text = it
        }
    }
}