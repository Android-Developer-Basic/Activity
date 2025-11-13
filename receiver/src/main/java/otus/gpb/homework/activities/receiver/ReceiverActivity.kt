package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val intent = intent.getBundleExtra("payload")

        findViewById<TextView>(R.id.titleTextView).run {
            text = intent?.getString("title")
        }

        findViewById<TextView>(R.id.yearTextView).run {
            text = intent?.getString("year")
        }

        findViewById<TextView>(R.id.descriptionTextView).run {
            text = intent?.getString("description")
        }

        findViewById<ImageView>(R.id.posterImageView).run {
            when (intent?.getString("title")) {
                "Славные парни" -> setImageResource(R.drawable.niceguys)
                "Интерстеллар" -> setImageResource(R.drawable.interstellar)
            }
        }

    }
}
