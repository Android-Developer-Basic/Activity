package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySenderBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.btnToGoogleMaps.setOnClickListener {
            val geoUriString = "geo:0,0?q=Moscow+restaurants&z=8"
            val geoUri = Uri.parse(geoUriString)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = geoUri
                setPackage("com.google.android.apps.maps")
            }

            startActivity(intent)
        }

        binding.btnSendEmail.setOnClickListener {
            val email = arrayOf("android@otus.ru")
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                putExtra(Intent.EXTRA_EMAIL, email)
                putExtra(Intent.EXTRA_SUBJECT, "Homework")
                putExtra(Intent.EXTRA_TEXT, "Some text")
                data = Uri.parse("mailto:")
            }

            startActivity(intent)
        }

        binding.btnOpenReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/plain"
                putExtra("title", getString(R.string.title))
                putExtra("year", getString(R.string.year))
                putExtra("description", getString(R.string.description))
            }

            startActivity(intent)
        }
    }
}
