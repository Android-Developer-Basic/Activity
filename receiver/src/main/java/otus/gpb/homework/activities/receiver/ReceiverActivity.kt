package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val title = intent.getStringExtra("title")

        binding.titleTextView.text = title
        binding.yearTextView.text = intent.getStringExtra("year")
        binding.descriptionTextView.text = intent.getStringExtra("description")

        binding.posterImageView.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                when (title) {
                    "Славные парни" -> R.drawable.niceguys
                    "Интерстеллар" -> R.drawable.interstellar
                    else -> -1
                }
            )
        )
    }
}
