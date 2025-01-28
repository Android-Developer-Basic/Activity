package otus.gpb.homework.activities.receiver


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val intent = intent
        if (intent.action == Intent.ACTION_SEND) {

            val titleTextView = findViewById<TextView>(R.id.titleTextView)
            val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
            val yearTextView = findViewById<TextView>(R.id.yearTextView)
            val posterImageView = findViewById<ImageView>(R.id.posterImageView)

            val keyTitle = intent.extras?.getString("key_title")
            val keyYear = intent.extras?.getString("key_year")
            val keyDescription = intent.extras?.getString("key_description")
            val posterImage = getDrawable(R.drawable.niceguys)

            titleTextView.text = keyTitle
            descriptionTextView.text = keyDescription
            yearTextView.text = keyYear
            posterImageView.setImageDrawable(posterImage)
        }
    }
}
