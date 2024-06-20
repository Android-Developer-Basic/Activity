package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.extras?.getString("title")
        findViewById<TextView>(R.id.titleTextView).text = title
        val year = intent.extras?.getString("year")
        findViewById<TextView>(R.id.yearTextView).text = year
        val description = intent.extras?.getString("description")
        findViewById<TextView>(R.id.descriptionTextView).text = description
        if (year == "2014") {
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.interstellar))
        }
        else findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.niceguys))

    }
}
