package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity(R.layout.sender_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()

        val buttonToGoogleMaps = findViewById<Button>(R.id.buttonToGoogleMaps)
        buttonToGoogleMaps.setOnClickListener {
            val nearbyRestaurantsUri = Uri.parse("geo:0,0?q=Рестораны")
            val mapIntent = Intent(Intent.ACTION_VIEW, nearbyRestaurantsUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        val buttonSendEmail = findViewById<Button>(R.id.buttonSendEmail)
        buttonSendEmail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto: android@otus.ru?subject=Otus' President&body=How is it going")
            )
            startActivity(emailIntent)
        }

        val buttonOpenReceiver = findViewById<Button>(R.id.buttonOpenReceiver)
        val niceGuy = Payload(
            "Хорошие Парни",
            "2020",
            "Тестовое описание"
        )
        buttonOpenReceiver.setOnClickListener {
            val openReceiverIntent = Intent(Intent.ACTION_SEND)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .setType("text/plain")
                .putExtra("title", niceGuy.title)
                .putExtra("year", niceGuy.year)
                .putExtra("description", niceGuy.description)

            startActivity(openReceiverIntent)
        }
    }

}