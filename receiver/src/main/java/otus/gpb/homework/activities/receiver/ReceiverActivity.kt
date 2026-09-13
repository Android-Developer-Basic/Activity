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


        val posterImageView = findViewById<ImageView>(R.id.posterImageView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)

        if (intent.getStringExtra("title") == "Интерстеллар") {
            val poster = AppCompatResources.getDrawable(this, R.drawable.interstellar)
            posterImageView.setImageDrawable(poster)
        } else {
            val poster = AppCompatResources.getDrawable(this, R.drawable.niceguys)
            posterImageView.setImageDrawable(poster)
        }

        descriptionTextView.text = intent.getStringExtra("description")

        yearTextView.text = intent.getStringExtra("year")

        titleTextView.text = intent.getStringExtra("title")
    }
}
