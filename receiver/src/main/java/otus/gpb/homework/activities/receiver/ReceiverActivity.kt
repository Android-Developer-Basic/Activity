package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        findViewById<TextView>(R.id.titleTextView).run {
            text = intent.getStringExtra("title").orEmpty()
        }
        findViewById<TextView>(R.id.yearTextView).run {
            text = intent.getStringExtra("year").orEmpty()
        }
        findViewById<TextView>(R.id.descriptionTextView).run {
            text = intent.getStringExtra("desc").orEmpty()
        }
        findViewById<ImageView>(R.id.posterImageView).run {
            when (intent.getStringExtra("title").orEmpty()) {
                "niceguys" -> setImageResource(R.drawable.niceguys)
                "interstellar" -> setImageResource(R.drawable.interstellar)
            }
        }
    }
}
