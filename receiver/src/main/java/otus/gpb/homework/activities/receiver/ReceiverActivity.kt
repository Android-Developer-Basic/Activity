package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    private val posters: MutableList<PosterImage> = mutableListOf()
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var posterImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        yearTextView = findViewById(R.id.yearTextView)
        posterImageView = findViewById(R.id.posterImageView)

        getPosterImages()
        fillViews()

    }

    private fun fillViews() {
        titleTextView.text = intent.getStringExtra("title")
        descriptionTextView.text = intent.getStringExtra("description")
        yearTextView.text = intent.getStringExtra("year")

        setImage(yearTextView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getPosterImages() {
        val niceGuysImage: Drawable? = getDrawable(R.drawable.niceguys)
        val interstellarImage: Drawable? = getDrawable(R.drawable.interstellar)

        posters.add(PosterImage(niceGuysImage, "2016"))
        posters.add(PosterImage(interstellarImage, "2014"))

    }

    private fun setImage(year: TextView) {
        val draw = posters.first { it.year == year.text }
        posterImageView.setImageDrawable(draw.img)
    }
}