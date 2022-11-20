package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val intent = intent
        findViewById<TextView>(R.id.titleTextView).text = intent.getStringExtra("title")
            //intent.getStringExtra("title")
        findViewById<TextView>(R.id.descriptionTextView).text= intent.getStringExtra("description")
        findViewById<TextView>(R.id.yearTextView).text= intent.getStringExtra("year")
        if (intent.getStringExtra("title") == "Интерстеллар")
        {
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(this.getDrawable(R.drawable.interstellar))
        }
        else
        {
            if (intent.getStringExtra("title") == "Славные парни")
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(this.getDrawable(R.drawable.niceguys))
        }

    }
}