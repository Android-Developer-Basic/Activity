package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity() {
    private lateinit var posterImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        posterImageView = findViewById(R.id.posterImageView)
        titleTextView = findViewById(R.id.titleTextView)
        yearTextView = findViewById(R.id.yearTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        processIntent(intent)
    }

    private fun processIntent(intent: Intent) {
        val title = intent.getStringExtra("title")
        val drawable = when (title) {
            "Интерстеллар" -> AppCompatResources.getDrawable(
                applicationContext,
                R.drawable.interstellar
            )
            "Славные парни" -> AppCompatResources.getDrawable(
                applicationContext,
                R.drawable.niceguys
            )
            else -> null
        }
        if (drawable != null) posterImageView.setImageDrawable(drawable)
        titleTextView.text = title
        yearTextView.text = intent.getStringExtra("year")
        descriptionTextView.text = intent.getStringExtra("description")
    }

}
