package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    val imageMap = mapOf("Славные парни" to R.drawable.niceguys, "Интерстеллар" to R.drawable.interstellar)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent.getStringExtra("title")
        val year = intent.getStringExtra("year")
        val description = intent.getStringExtra("description")

        Log.d("findViewById ReceiverActivity", title ?: "")
        Log.d("findViewById ReceiverActivity", year ?: "")
        Log.d("findViewById ReceiverActivity", description ?: "")
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description

        findViewById<ImageView>(R.id.posterImageView).setImageResource(imageMap[title] ?: 0)
    }
}
