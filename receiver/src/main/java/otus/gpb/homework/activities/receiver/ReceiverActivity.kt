package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.extras?.getString("title") ?: ""
        val year = intent.extras?.getString("year")
        val description = intent.extras?.getString("description")

        //Log.d("Receiver title", title.toString())
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description
        if (title.equals("Интерстеллар")) {
            findViewById<ImageView>(R.id.posterImageView).setImageResource(R.drawable.interstellar)
        } else if (title.equals("Славные парни")) {
            findViewById<ImageView>(R.id.posterImageView).setImageResource(R.drawable.niceguys)
        }

    }
}