package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiverBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent

        val title = intent.getStringExtra("title:")
        binding.titleTextView.text  = title
        binding.yearTextView.text = intent.getStringExtra("year:")
        binding.descriptionTextView.text = intent.getStringExtra("description:")
        if (title.equals("interstellar")){
            binding.posterImageView.setImageDrawable(getDrawable(R.drawable.interstellar))
        } else{
            binding.posterImageView.setImageDrawable(getDrawable(R.drawable.niceguys))
        }
    }
}