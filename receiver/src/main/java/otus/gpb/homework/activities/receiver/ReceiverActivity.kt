package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title: String = intent.getStringExtra(TITLE_KEY).toString()
        val year: String = intent.getStringExtra(YEAR_KEY).toString()
        val description: String = intent.getStringExtra(DESCRIPTION_KEY).toString()

        binding.titleTextView.text = title
        binding.yearTextView.text = year
        binding.descriptionTextView.text = description
        binding.posterImageView.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.niceguys
            )
        )
    }

    companion object {
        const val TITLE_KEY = "title key"
        const val YEAR_KEY = "year key"
        const val DESCRIPTION_KEY = "description key"
    }
}
