package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {
    val binding : ActivityReceiverBinding by lazy { ActivityReceiverBinding.inflate(layoutInflater) }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.titleTextView.text = intent.getStringExtra("title")
        binding.yearTextView.text = intent.getStringExtra("year")
        binding.descriptionTextView.text = intent.getStringExtra("description")
        binding.posterImageView.setImageDrawable(applicationContext.getDrawable(R.drawable.niceguys))
    }
}