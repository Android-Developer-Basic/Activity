package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {
    private lateinit var img:ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewYear: TextView
    private lateinit var textViewDescription: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        img = findViewById(R.id.posterImageView)
        textViewTitle = findViewById(R.id.titleTextView)
        textViewYear = findViewById(R.id.yearTextView)
        textViewDescription = findViewById(R.id.descriptionTextView)

        textViewTitle.text = intent.extras?.getString("title")
        textViewYear.text = intent.extras?.getString("year")
        textViewDescription.text = intent.extras?.getString("description")

        img.setImageResource(R.drawable.niceguys)

    }
}