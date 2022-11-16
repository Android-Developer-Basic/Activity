package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dto.MovieDTO

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        intent.getParcelableExtra<MovieDTO>("dataset")?.let {
            findViewById<TextView>(R.id.titleTextView).text = it.title
            findViewById<TextView>(R.id.yearTextView).text = it.year
            findViewById<TextView>(R.id.descriptionTextView).text = it.description

            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.interstellar))
        }
    }
}