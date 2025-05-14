package otus.gpb.homework.activities.receiver

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat


const val KEY_PAYLOAD = "key_payload"

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        getSupportActionBar()?.hide()
        val ttv = findViewById<TextView>(R.id.titleTextView)
        val ytv = findViewById<TextView>(R.id.yearTextView)
        val dtv = findViewById<TextView>(R.id.descriptionTextView)
        val piv = findViewById<ImageView>(R.id.posterImageView)
        val p = IntentCompat.getParcelableExtra(intent, KEY_PAYLOAD, otus.gpb.homework.activities.mylibrary2.Payload::class.java)
        ttv.text = p?.title
        ytv.text = p?.year
        dtv.text = p?.description
        when (p?.title) {
            "Интерстеллар" ->
                piv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.interstellar))
            "Славные парни" ->
                piv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.niceguys))
        }
    }
}
