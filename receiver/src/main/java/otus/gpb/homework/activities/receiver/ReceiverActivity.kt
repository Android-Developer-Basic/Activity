package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val poster = findViewById<ImageView>(R.id.posterImageView). apply {
            setImageDrawable(getDrawable(R.drawable.niceguys))
        }

        val title = findViewById<TextView>(R.id.titleTextView).apply {
            val unWrappedTitle = intent.getStringExtra("title")
            text = unWrappedTitle
        }
        val description = findViewById<TextView>(R.id.descriptionTextView).apply {
            val unWrappedDescription = intent.getStringExtra("description")
            text = unWrappedDescription
        }
        val year = findViewById<TextView>(R.id.yearTextView).apply {
            val unWrappedYear = intent.getStringExtra("year")
            text = unWrappedYear
        }
    }
}