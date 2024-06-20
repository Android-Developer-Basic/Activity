package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val intent = intent

        findViewById<TextView>(R.id.titleTextView).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.yearTextView).text = intent.getStringExtra("year")
        findViewById<TextView>(R.id.descriptionTextView).text = intent.getStringExtra("description")

        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.niceguys))
    }
}
