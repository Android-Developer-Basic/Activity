package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        intent?.let { intent ->
            val title = intent.getStringExtra("title")
            val year = intent.getStringExtra("year")
            val description = intent.getStringExtra("description")

            val titleTextView: TextView = findViewById(R.id.titleTextView)
            titleTextView.text = title

            val yearTextView: TextView = findViewById(R.id.yearTextView)
            yearTextView.text = year

            val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
            descriptionTextView.text = description

            val posterImageView: ImageView = findViewById(R.id.posterImageView)

            when (title) {
                "Славные парни" -> posterImageView.setImageResource(R.drawable.niceguys)
                "Интерстеллар" -> posterImageView.setImageResource(R.drawable.interstellar)
            }
        }
    }
}