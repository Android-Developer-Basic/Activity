package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding
import java.util.Locale

class ReceiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        android.util.Log.d("RECEIVER_DEBUG", "ReceiverActivity created")
        android.util.Log.d("RECEIVER_DEBUG", "Intent action: ${intent.action}")
        android.util.Log.d("RECEIVER_DEBUG", "Intent type: ${intent.type}")

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {
        val title = intent.getStringExtra("title") ?: "No title"
        val year = intent.getStringExtra("year") ?: "No year"
        val description = intent.getStringExtra("description") ?: "No description"

        binding.titleTextView.text = title
        binding.yearTextView.text = year
        binding.descriptionTextView.text = description

        setPosterImage(title)
    }

    private fun setPosterImage(movieTitle: String) {
        val drawableResId = when (movieTitle.lowercase(Locale.ROOT)) {
            "interstellar" -> R.drawable.interstellar
            "inception" -> R.drawable.inception
            "niceguys" -> R.drawable.niceguys
            else -> R.drawable.interstellar
        }

        val drawable = getDrawable(drawableResId)
        binding.posterImageView.setImageDrawable(drawable)
    }
}