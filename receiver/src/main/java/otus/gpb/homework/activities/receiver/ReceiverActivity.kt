package otus.gpb.homework.activities.receiver

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val context = this
        val newTitle = intent.getStringExtra("title")
        val newYear = intent.getStringExtra("year")
        val newDescription = intent.getStringExtra("description")
        val newPoster = context.getDrawable(R.drawable.niceguys)
        findViewById<TextView>(R.id.titleTextView).text = newTitle
        findViewById<TextView>(R.id.yearTextView).text = newYear
        findViewById<TextView>(R.id.descriptionTextView).text = newDescription
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(newPoster)
    }
}