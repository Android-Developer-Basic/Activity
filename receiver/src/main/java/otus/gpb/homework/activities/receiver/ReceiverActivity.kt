package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity() {
    private val NOT_FOUND_TEMPLATE: String = " text is not found"
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
        val title = intent.extras?.get("title")?.toString() ?: "Title$NOT_FOUND_TEMPLATE"
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
        posterImageView.setImageDrawable(drawable)
        titleTextView.text = title
        yearTextView.text = intent.extras?.get("year")?.toString() ?: "Year$NOT_FOUND_TEMPLATE"
        descriptionTextView.text =
            intent.extras?.get("description")?.toString() ?: "Description$NOT_FOUND_TEMPLATE"
    }

}
