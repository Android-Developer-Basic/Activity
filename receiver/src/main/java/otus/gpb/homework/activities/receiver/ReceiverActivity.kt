package otus.gpb.homework.activities.receiver

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class ReceiverActivity : AppCompatActivity(R.layout.activity_receiver) {

    override fun onStart() {
        super.onStart()

        intent?.let { intent ->
            val title = intent.getStringExtra("title")
            val year = intent.getStringExtra("year")
            val description = intent.getStringExtra("description")

            findViewById<TextView>(R.id.titleTextView).text = title
            findViewById<TextView>(R.id.yearTextView).text = year
            findViewById<TextView>(R.id.descriptionTextView).text = description
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.niceguys))
        }

    }
}