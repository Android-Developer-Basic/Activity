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

        val year = intent.getStringExtra("year")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val posterImageView = findViewById<ImageView>(R.id.posterImageView)

        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.descriptionTextView).text = description

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
        }
}
