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

        val title = intent.extras?.getString("title")
        val year = intent.extras?.getString("year")
        val description = intent.extras?.getString("description")

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description
        val imageView: ImageView = findViewById(R.id.posterImageView)
        val drawable = ContextCompat.getDrawable(this, R.drawable.interstellar)
        imageView.setImageDrawable(drawable)
    }
}
