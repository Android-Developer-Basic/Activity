package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding
import kotlin.random.Random


class SenderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bEmail.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.corporate_mail)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.topic))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.message))
            }.run { startActivity(this)}
        }

        binding.bMap.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=restaurants")
                setPackage("com.google.android.apps.maps")
            }.run { startActivity(this)}
        }

        binding.bReceiver.setOnClickListener {
            val idArrayMovie = when((1..2).random()){
                1 -> R.array.movie_1
                2 -> R.array.movie_2
                else -> R.array.movie_1
            }

            val resourceArrayMovie = resources.getStringArray(idArrayMovie)

            Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", resourceArrayMovie[0])
                putExtra("year", resourceArrayMovie[1])
                putExtra("description", resourceArrayMovie[2])
                putExtra("keyImage", resourceArrayMovie[3])
            }.run{ startActivity(this)}
        }
    }
}