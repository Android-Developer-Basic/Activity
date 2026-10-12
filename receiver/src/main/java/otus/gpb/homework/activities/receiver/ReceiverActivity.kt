package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.titleTextView.text = intent.getStringExtra("title").orEmpty()
        binding.yearTextView.text = intent.getStringExtra("year").orEmpty()
        binding.descriptionTextView.text = intent.getStringExtra("description").orEmpty()

        val posterResId = when (binding.titleTextView.text.toString().lowercase()) {
            "интерстеллар"   -> R.drawable.interstellar
            "славные парни"  -> R.drawable.niceguys
            else             -> R.drawable.niceguys
        }

        AppCompatResources.getDrawable(this, posterResId)?.let {
            binding.posterImageView.setImageDrawable(it)
        }
    }
}
