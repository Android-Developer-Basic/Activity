package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity(R.layout.activity_receiver) {

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.extras?.apply {
            val filename = when (getString("title")) {
                "Интерстеллар" -> "interstellar"
                "Славные парни" -> "niceguys"
                else -> null
            }
            filename?.let {
                findViewById<ImageView>(R.id.posterImageView).setImageDrawable(
                    AppCompatResources.getDrawable(
                        this@ReceiverActivity,
                        resources.getIdentifier(it, "drawable", packageName))
                )
            }
            findViewById<TextView>(R.id.titleTextView).text = getString("title")
            findViewById<TextView>(R.id.yearTextView).text = getString("year")
            findViewById<TextView>(R.id.descriptionTextView).text = getString("description")
        }
    }
}
