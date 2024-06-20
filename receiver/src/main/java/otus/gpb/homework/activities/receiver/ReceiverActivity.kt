package otus.gpb.homework.activities.receiver

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

class ReceiverActivity : AppCompatActivity() {
    companion object {
        const val THE_NICE_GUYS_MOVIE = "Славные парни"
        const val INTERSTELLAR_MOVIE = "Интерстеллар"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        findViewById<AppCompatTextView>(R.id.titleTextView)?.apply {
            text = intent.getStringExtra("MOVIE_NAME")

            when(text.toString()) {
                THE_NICE_GUYS_MOVIE -> loadImage(context, R.drawable.niceguys)
                INTERSTELLAR_MOVIE -> loadImage(context, R.drawable.interstellar)
            }
        }

        findViewById<AppCompatTextView>(R.id.yearTextView)?.apply {
            text = intent.getStringExtra("MOVIE_YEAR")
        }

        findViewById<AppCompatTextView>(R.id.descriptionTextView)?.apply {
            text = intent.getStringExtra("MOVIE_DESCRIPTION")
        }
    }
    private fun loadImage(context: Context, id: Int) {
        findViewById<AppCompatImageView>(R.id.posterImageView)?.apply {
            setImageDrawable(context.resources.getDrawable(id, theme))
        }
    }
}
