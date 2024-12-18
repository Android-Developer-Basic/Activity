package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R

const val titleKey = "titleKey"
const val titleyear = "titleyear"
const val titledescription = "titledescription"


class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val buttonToGoogleMaps = findViewById<Button>(R.id.to_google_maps)
        buttonToGoogleMaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=restaurant"))
                .setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (_: Exception) {
            }

        }
        val buttonSendEmail = findViewById<Button>(R.id.send_email)
        buttonSendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:android@otus.ru"))
            intent.putExtra(Intent.EXTRA_TEXT, "Text of mail")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of mail")
            startActivity(intent)
        }
        val buttonOpenReceiver = findViewById<Button>(R.id.open_receiver)
        buttonOpenReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
                .addCategory("android.intent.category.DEFAULT")
                .putExtra(titleKey, getString(R.string.title))
                .putExtra(titleyear, getString(R.string.year))
                .putExtra(titledescription, getString(R.string.description))
            startActivity(intent)
        }
    }
}