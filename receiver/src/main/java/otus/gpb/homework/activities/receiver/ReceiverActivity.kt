package otus.gpb.homework.activities.receiver

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        intent?.extras?.let { defaultViewModelCreationExtras ->
            defaultViewModelCreationExtras.getString("title")?.let {
                findViewById<TextView>(R.id.titleTextView).text = it

                when (it) {
                    "Интерстеллар" -> ContextCompat.getDrawable(this, R.drawable.interstellar)
                    "Славные парни" -> ContextCompat.getDrawable(this, R.drawable.niceguys)
                    else -> null
                }?.let { drawable ->
                    findViewById<ImageView>(R.id.posterImageView).setImageDrawable(drawable)
                } ?: findViewById<ImageView>(R.id.posterImageView).setVisibility(View.GONE)
            }
            defaultViewModelCreationExtras.getString("year")?.let {
                findViewById<TextView>(R.id.yearTextView).text = it
            }
            defaultViewModelCreationExtras.getString("description")?.let {
                findViewById<TextView>(R.id.descriptionTextView).text = it
            }

        }
    }
}
