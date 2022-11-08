package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.to_google_maps_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=restaurants"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        findViewById<Button>(R.id.send_email_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Important information")
                putExtra(Intent.EXTRA_TEXT, "The weather is really nice!")
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.open_receiver_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                val payload = PAYLOAD_DB.shuffled().take(1)[0]
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", payload.title)
                putExtra("year", payload.year)
                putExtra("description", payload.description)
            }
            startActivity(intent)
        }
    }
}
