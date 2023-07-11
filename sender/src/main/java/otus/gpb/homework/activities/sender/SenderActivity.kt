package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class SenderActivity : AppCompatActivity() {

    private val toGoogleMaps by lazy { findViewById<Button>(R.id.ToGoogleMaps) }
    private val sendEmail by lazy { findViewById<Button>(R.id.SendEmail) }
    private val OpenReceiver by lazy { findViewById<Button>(R.id.OpenReceiver) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        toGoogleMaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Рестораны"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        sendEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:android@otus.ru"))
            startActivity(emailIntent)
        }

        OpenReceiver.setOnClickListener {
            val payload = Payload(
                title = "nice guys",
                year = "2016",
                description = "описание фильма"
            )

            val bundle = Bundle().apply {
                putString("title", payload.title)
                putString("year", payload.year)
                putString("description", payload.description)
            }
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("film", bundle)
            }
            startActivity(intent)
        }

    }

}