package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
    }

    override fun onStart() {
        super.onStart()

        intent.getBundleExtra("payload")?.let { payload ->
            payload.getString("title")?.let { title ->
                findViewById<TextView>(R.id.titleTextView).text = title

                getDrawableOfPosterImageByTitle(title)?.let { posterImageDrawable ->
                    findViewById<ImageView>(R.id.posterImageView)
                        .setImageDrawable(posterImageDrawable)
                }
            }
            payload.getString("year")?.let { year ->
                findViewById<TextView>(R.id.yearTextView).text = year
            }
            payload.getString("description")?.let { desc ->
                findViewById<TextView>(R.id.descriptionTextView).text = desc
            }
        } ?: run {
            finish()
        }
    }

    private fun getResourceOfPosterImageByTitle(title: String): Int? = when (title) {
        "Славные парни" -> R.drawable.niceguys
        "Интерстеллар" -> R.drawable.interstellar
        else -> null
    }

    private fun getDrawableOfPosterImageByTitle(title: String): Drawable? =
        getResourceOfPosterImageByTitle(title)?.let { posterImageResource ->
            return AppCompatResources.getDrawable(this, posterImageResource)
        }
}