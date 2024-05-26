package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class ReceiverActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var descriptionTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        imageView = findViewById(R.id.posterImageView)
        titleTextView = findViewById(R.id.titleTextView)
        yearTextView = findViewById(R.id.yearTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)

        titleTextView.text = intent.extras?.getString("title")
        yearTextView.text = intent.extras?.getString("year")
        descriptionTextView.text = intent.extras?.getString("description")

        val image = intent.extras?.getString("image", "interstellar")
        val drawable: Drawable? = when(image) {
            "interstellar" -> ResourcesCompat.getDrawable(getResources(), R.drawable.interstellar, null)
            "niceguys" -> ResourcesCompat.getDrawable(getResources(), R.drawable.niceguys, null)
            else ->  null
        }

        imageView.setImageDrawable(drawable)
    }
}
