package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dto.MovieDescriptionDTO

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        intent.getParcelableExtra<MovieDescriptionDTO>("data")?.let {
            findViewById<TextView>(R.id.descriptionTextView).text = it.description
            findViewById<TextView>(R.id.titleTextView).text = it.title
            findViewById<TextView>(R.id.yearTextView).text = it.year.toString()
            // Пока не ясно как смапить ресурсы
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(0))
        }
    }
}