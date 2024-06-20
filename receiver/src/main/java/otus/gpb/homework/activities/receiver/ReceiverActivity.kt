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

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val posterImageView = findViewById<ImageView>(R.id.posterImageView)

        intent.extras?.let {
            titleTextView.text = it.getString("title", ":( no title")
            descriptionTextView.text = it.getString("description", ":( no description")
            yearTextView.text = it.getString("year", ":( no year")

            when (it.getString("title", "").lowercase())
            {
                "интерстеллар" -> posterImageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.interstellar))
                "славные парни" -> posterImageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.niceguys))
            }
        }
    }
}
