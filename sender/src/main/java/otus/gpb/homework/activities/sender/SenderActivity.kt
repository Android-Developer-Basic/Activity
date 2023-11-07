package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonToMaps = findViewById<Button>(R.id.to_google_maps)
        val buttonToSendEmail = findViewById<Button>(R.id.send_email)
        val buttonOpenReceiver = findViewById<Button>(R.id.open_receiver)

        buttonToMaps.setOnClickListener {
            openGoogleMaps()
        }

        buttonToSendEmail.setOnClickListener {
            sendEmail()
        }

        buttonOpenReceiver.setOnClickListener {
            openReceiver()
        }
    }


    private fun openGoogleMaps() {
        val query = "restaurants"
        val uri = Uri.parse("geo:0,0?q=$query")

        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")

        startActivity(mapIntent)
    }

    private fun sendEmail() {
        val subject = "Check title"
        val body = "Check body"
        val address = "mailto:android@otus.ru?subject=$subject&body=$body"

        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse(address)
        )
        startActivity(intent)
    }

    private fun openReceiver() {
        val title = "Интерстеллар"
        val year = "2014"
        val description = "Фильм про космос..."

        val interstellar = Payload(
            title,
            year,
            description
        )

        val intent = Intent(Intent.ACTION_SEND)
            .addCategory(Intent.CATEGORY_DEFAULT)
            .setType("text/plain")
            .putExtra("title", interstellar.title)
            .putExtra("year", interstellar.year)
            .putExtra("description", interstellar.description)

        startActivity(intent)
    }
}