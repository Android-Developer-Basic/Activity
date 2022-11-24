package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ReceiverActivity : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var titleTv: TextView
    private lateinit var descTv: TextView
    private lateinit var yearTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        poster = findViewById<ImageView>(R.id.posterImageView)
        titleTv = findViewById<TextView>(R.id.titleTextView)
        descTv = findViewById<TextView>(R.id.descriptionTextView)
        yearTv = findViewById<TextView>(R.id.yearTextView)

        val title = intent?.extras?.getString("title", EMPTY_STRING)
        val year = intent?.extras?.getString("year", EMPTY_STRING)
        val description = intent?.extras?.getString("description", EMPTY_STRING)

        poster.setImageResource(R.drawable.niceguys)
        titleTv.text = title
        descTv.text = description
        yearTv.text = year

        }





    companion object {

        private const val EMPTY_STRING = ""
    }


}
