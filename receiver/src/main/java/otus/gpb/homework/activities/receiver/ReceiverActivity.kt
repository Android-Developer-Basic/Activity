package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {
    lateinit var binding: ActivityReceiverBinding
    private val mapImageKey = mapOf("movie_1" to R.drawable.niceguys, "movie_2" to R.drawable.interstellar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTextView.text = intent.getStringExtra("title")
        binding.yearTextView.text = intent.getStringExtra("year")
        binding.descriptionTextView.text = intent.getStringExtra("description")
        binding.posterImageView.setImageDrawable(ContextCompat.getDrawable(this, mapImageKey[intent.getStringExtra("keyImage")]!!))
    }
}
