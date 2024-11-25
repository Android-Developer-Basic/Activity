package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val payload = parseIntent(this)
        setContents(this, payload)
    }
}

fun parseIntent(receiverActivity: ReceiverActivity): Payload {

    val title: String = receiverActivity.intent.extras?.getString("title").toString()
    val description: String =
        receiverActivity.intent.extras?.getString("description").toString()
    val year: String = receiverActivity.intent.extras?.getString("year").toString()

    return Payload(title, description, year)
}

fun setContents(receiverActivity: ReceiverActivity, payload: Payload) {

    val currentImage =
        when (payload.title) {
            "Интерстеллар" -> receiverActivity.getDrawable(R.drawable.interstellar)
            "Славные парни" -> receiverActivity.getDrawable(R.drawable.niceguys)
            else -> {
               return
            }
        }

    receiverActivity.findViewById<TextView>(R.id.titleTextView).text = payload.title
    receiverActivity.findViewById<TextView>(R.id.descriptionTextView).text = payload.description
    receiverActivity.findViewById<TextView>(R.id.yearTextView).text = payload.year
    receiverActivity.findViewById<ImageView>(R.id.posterImageView).setImageDrawable(currentImage)
}

class Payload(
    val title: String,
    val description: String,
    val year: String,
)

