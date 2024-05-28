package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import otus.gpb.homework.payload.Payload

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val payload = Payload(
            intent.getStringExtra(Payload.TITLE).orEmpty(),
            intent.getStringExtra(Payload.YEAR).orEmpty(),
            intent.getStringExtra(Payload.DESC).orEmpty()
        )


        val posterImageView = findViewById<ImageView>(R.id.posterImageView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)

        when (payload.title) {
            "Славные парни" -> posterImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.niceguys
                )
            )

            "Интерстеллар" -> posterImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.interstellar
                )
            )
        }

        titleTextView.text = payload.title
        yearTextView.text = payload.year
        descriptionTextView.text = payload.description
    }
}
