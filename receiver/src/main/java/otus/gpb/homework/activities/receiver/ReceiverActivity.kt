package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val title = intent.getStringExtra("title")
        val year = intent.getStringExtra("year")
        val description = intent.getStringExtra("description")

        findViewById<TextView>(R.id.descriptionTextView).text = description
        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year

        if (title !== null) {
            val resourceId = getIdByTitle(title)
            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(resourceId))
        }
    }

    private fun getIdByTitle(title: String): Int {
        return when (title) {
            "Славные парни" -> R.drawable.niceguys
            "Интерстеллар" -> R.drawable.interstellar
            else -> 0
        }
    }
}