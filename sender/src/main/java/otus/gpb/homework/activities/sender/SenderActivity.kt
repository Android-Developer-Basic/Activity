package otus.gpb.homework.activities.sender

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent
import android.net.Uri

import otus.gpb.homework.activities.sender.R

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

        val btnMaps = findViewById<Button>(R.id.btnMaps)
        btnMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=рестораны")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        val btnEmail = findViewById<Button>(R.id.btnSendEmail)
        btnEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Basic Android. Home Work #6.")
                putExtra(Intent.EXTRA_TEXT, "Привет! Это тестовое сообщение ДЗ #6 по Андроид.")
            }
            startActivity(emailIntent)
        }

        val btnReceiver = findViewById<Button>(R.id.btnOpenReceiver)
        btnReceiver.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)

                // Данные из payload.txt (пример для фильма "Интерстеллар")
                putExtra("title", "Интерстеллар")
                putExtra("year", "2014")
                putExtra("description", "Фантастический фильм о путешествии " +
                        "через червоточину с целью найти новый дом для человечества.")
            }
            startActivity(sendIntent)
        }
    }
}