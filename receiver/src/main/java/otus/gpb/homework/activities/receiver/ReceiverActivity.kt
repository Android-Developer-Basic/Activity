package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {
    private val title by lazy { findViewById<TextView>(R.id.titleTextView)}
    private val year by lazy { findViewById<TextView>(R.id.yearTextView)}
    private val description by lazy { findViewById<TextView>(R.id.descriptionTextView)}
    private val poster by lazy { findViewById<ImageView>(R.id.posterImageView)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val film = intent.getBundleExtra(KEY) /*?: throw Exception("no such key $KEY exists")*/
        val nameFilm  =  film?.getString("title")?: UNKNOWN_FILM
        title.text =nameFilm
        year.text = film?.getString("year")?: UNKNOWN_FILM
        description.text = film?.getString("description")?: UNKNOWN_FILM

        when(nameFilm){
            "nice guys" -> poster.setImageResource(R.drawable.niceguys)
            "interstellar" -> poster.setImageResource(R.drawable.interstellar)
            else -> poster.setImageDrawable(null)
        }
    }
    companion object{
        private const val KEY = "film"
        private const val UNKNOWN_FILM = "Unknown film"
    }
}
