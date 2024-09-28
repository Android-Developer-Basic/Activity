package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.andrusov.shared.Payload

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val payload = intent.getParcelableExtra("payload", Payload::class.java)

        findViewById<TextView>(R.id.titleTextView).text = payload?.title
        findViewById<TextView>(R.id.yearTextView).text = payload?.year
        findViewById<TextView>(R.id.descriptionTextView).text = payload?.description

        findViewById<ImageView>(R.id.posterImageView)
            .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.niceguys))
    }
}
