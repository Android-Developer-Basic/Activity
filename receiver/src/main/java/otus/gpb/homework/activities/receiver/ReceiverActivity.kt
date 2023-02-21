package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {
    private val posters: MutableList<PosterImage> = mutableListOf()
    private lateinit var titleTextView:TextView
    private lateinit var descriptionTextView:TextView
    private lateinit var yearTextView:TextView
    private lateinit var posterImageView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        //Views
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        yearTextView = findViewById(R.id.yearTextView)
        posterImageView = findViewById(R.id.posterImageView)

        //Calls
        getPosterImages()
        fillViews()
        setImage()
    }
    //Data from Sender
    private fun fillViews(){
            titleTextView.text = intent.getStringExtra("T")
            descriptionTextView.text = intent.getStringExtra("D")
            yearTextView.text = intent.getStringExtra("Y")

        }
    //fill posters list
    private fun getPosterImages() {
        val niceGuysImage: Drawable? = getDrawable(R.drawable.niceguys)
        val  interstellarImage: Drawable? = getDrawable(R.drawable.interstellar)

        posters.add(PosterImage(niceGuysImage, "niceGuys"))
        posters.add(PosterImage(interstellarImage, "interstellar"))
    }
    private fun setImage(){
        val tag = intent.getStringExtra("tag")
        posters.map{if(it.tag == tag) posterImageView.setImageDrawable(it.img); return@map}
    }

}