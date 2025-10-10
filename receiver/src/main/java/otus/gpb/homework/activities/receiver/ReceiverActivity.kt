package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val filmTitle: TextView = findViewById(R.id.titleTextView)
        filmTitle.text = intent.extras?.getString("title").orEmpty()

        val filmYear: TextView = findViewById(R.id.yearTextView)
        filmYear.text = intent.extras?.getString("year").orEmpty()

        val filmDescription: TextView = findViewById(R.id.descriptionTextView)
        filmDescription.text = intent.extras?.getString("description").orEmpty()

        val filmPosterView: ImageView = findViewById(R.id.posterImageView)

        var filmPicture: Drawable? = null
        when (filmTitle.text) {
            "Славные парни" -> filmPicture = getDrawable( R.drawable.niceguys )
            "Интерстеллар" -> filmPicture = getDrawable( R.drawable.interstellar )
        }
        if( filmPicture != null )  filmPosterView.setImageDrawable( filmPicture )
    }
}
