package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ReceiverActivity", "onCreate() called")

        val title: String = intent.getStringExtra(TITLE_KEY) ?: "no title"
        val year: String = intent.getStringExtra(YEAR_KEY) ?: "no year"
        val description: String = intent.getStringExtra(DESCRIPTION_KEY) ?: "no description"

        Log.d("ReceiverActivity", "Title: $title, Year: $year, Description: $description")

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
