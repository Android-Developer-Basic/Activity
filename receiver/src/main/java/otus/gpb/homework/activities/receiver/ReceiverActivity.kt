package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.extras?.getString("title")
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = intent.extras?.getString("year")
        findViewById<TextView>(R.id.descriptionTextView).text = intent.extras?.getString("description")
        val poster = findViewById<ImageView>(R.id.posterImageView)
        when (title) {
            "Славные парни" -> poster.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.niceguys))
            "Интерстеллар" -> poster.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.interstellar))
        }
    }
}