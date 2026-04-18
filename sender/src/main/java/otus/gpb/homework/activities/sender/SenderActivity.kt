package otus.gpb.homework.activities.sender

import android.content.Intent
import android.icu.util.ULocale
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R
import kotlin.jvm.java
import androidx.core.net.toUri

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

        findViewById<Button>(R.id.GoogleMapsButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "geo:0,0?q=restaurants".toUri())
                .setPackage("com.google.android.apps.maps")

            startActivity(intent)
        }

        findViewById<Button>(R.id.SendEmailButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, "mailto:android@otus.ru".toUri())
                .putExtra(Intent.EXTRA_SUBJECT, "Тут вот тема письма.")
                .putExtra(Intent.EXTRA_TEXT, "А тут нужно тело письма написать.")

            startActivity(intent)
        }

        findViewById<Button>(R.id.OpenReceiverButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .addCategory(Intent.CATEGORY_DEFAULT)
                .putExtra("title", "Славные парни")
                .putExtra("year", "2016")
                .putExtra("description",
                    "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
                )

            startActivity(intent)
        }
    }
}