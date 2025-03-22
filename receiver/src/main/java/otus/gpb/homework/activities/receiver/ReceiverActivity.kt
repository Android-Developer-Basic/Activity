package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

const val PLD_NAME = "title"
const val PLD_YEAR = "year"
const val PLD_DESC = "description"

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        //Toast.makeText(this, "Receiver: onCreate", Toast.LENGTH_SHORT).show()

        val name: String = intent.extras?.getString(PLD_NAME) ?: "Unknown title"
        val year: String = intent.extras?.getString(PLD_YEAR) ?: "Unknown year"
        val desc: String = intent.extras?.getString(PLD_DESC) ?: "Unknown description"
        val image: Drawable? = when (name) {
            "Славные парни" -> ResourcesCompat.getDrawable(this.resources, R.drawable.niceguys, null)
            "Интерстеллар" -> ResourcesCompat.getDrawable(this.resources, R.drawable.interstellar, null)
            else -> null
        }

        findViewById<TextView>(R.id.titleTextView).text = name
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = desc
        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(image)
    }
}
