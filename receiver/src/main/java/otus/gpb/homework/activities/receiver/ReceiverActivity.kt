package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity(R.layout.activity_receiver) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.extras?.getString("title")


        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.descriptionTextView).text =
            intent.extras?.getString("description")
        findViewById<TextView>(R.id.yearTextView).text = intent.extras?.getString("year")


        val image: ImageView = findViewById(R.id.posterImageView)

        when (title) {
            "Славные парни" -> image.setImageResource(R.drawable.niceguys)
            "Интерстеллар" -> image.setImageResource(R.drawable.interstellar)
        }
    }
}
