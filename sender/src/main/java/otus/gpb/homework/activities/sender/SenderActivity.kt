package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R
import androidx.core.net.toUri

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        val buttonMaps = findViewById<Button>(R.id.ButtonMaps)
        val buttonEmail = findViewById<Button>(R.id.buttonEmail)
        val buttonReceiver = findViewById<Button>(R.id.buttonReceiver)

        buttonMaps.setOnClickListener {
            val restIntent = "geo:0,0?q=Рестораны".toUri()
            val mapIntent = Intent(Intent.ACTION_VIEW, restIntent)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        buttonEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:help@otus.ru".toUri()
                putExtra(Intent.EXTRA_SUBJECT, "Домашнее задание")
                putExtra(Intent.EXTRA_TEXT, "Помогите с домашним заданием, я не справляюсь")
            }
            startActivity(emailIntent)
        }

        buttonReceiver.setOnClickListener {
            val input = assets.open("payload.txt")
            val lines = input.bufferedReader().readLines()

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)

                putExtra("title", lines.getOrNull(0) ?.substringAfter("title:")?.trim() ?: "Unknown title")
                putExtra("year", lines.getOrNull(1) ?.substringAfter("year:")?.trim() ?: "Unknown Year")
                putExtra("description", lines.getOrNull(2) ?.substringAfter("description:")?.trim() ?: "No description")
            }
            startActivity(sendIntent)
        }

    }
}