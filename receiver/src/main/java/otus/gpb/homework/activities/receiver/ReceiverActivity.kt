package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.IntentCompat
import ru.tilman.payload.PAYLOAD_KEY
import ru.tilman.payload.Payload

class ReceiverActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val payload = IntentCompat.getParcelableExtra(intent, PAYLOAD_KEY, Payload::class.java)
        if (payload == null) {
            Toast.makeText(this, getString(R.string.payliad_not_found), Toast.LENGTH_SHORT).show()
            return
        }

        val imageVies = findViewById<ImageView>(R.id.posterImageView)
        val imageId = runCatching { imageVies.getImageIdByTitle(payload) }
            .getOrElse {
                Toast.makeText(
                    this,
                    String.format(getString(R.string.image_not_found), payload.title),
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        imageVies.setImageDrawable(getDrawableById(imageId))

        findViewById<TextView>(R.id.titleTextView)
            .apply { text = payload.title }
        findViewById<TextView>(R.id.descriptionTextView)
            .apply { text = payload.description }
        findViewById<TextView>(R.id.yearTextView)
            .apply { text = payload.year }

    }

    /**
     * Отступление от задания по рекомендации IDE использован [AppCompatResources]
     * */
    private fun getDrawableById(imageId: Int) =
        AppCompatResources.getDrawable(this, imageId)

    private fun ImageView.getImageIdByTitle(it: Payload) = when (it.title) {
        context.getString(R.string.niceguys_title) -> R.drawable.niceguys
        context.getString(R.string.interstellar_title) -> R.drawable.interstellar
        else -> throw IllegalArgumentException(
            String.format(
                context.getString(R.string.title_not_found),
                it.title
            )
        )
    }
}
