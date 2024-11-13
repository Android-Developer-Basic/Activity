package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SenderActivity : AppCompatActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toGoogleMaps = findViewById<Button>(R.id.to_google_maps)
        val sendMail = findViewById<Button>(R.id.send_mail)
        val openReceiver = findViewById<Button>(R.id.open_receiver)

        toGoogleMaps.setOnClickListener {
            val googleMapQuery = Uri.parse("geo:0,0?q=Рестораны")
            val intent = Intent(Intent.ACTION_VIEW, googleMapQuery).apply {
                setPackage("com.google.android.apps.maps")
            }
            startActivity(intent)
        }

        sendMail.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Домашнее задание")
                putExtra(Intent.EXTRA_TEXT, "Сделал домашнее задание Activity2")
                type = "message/rfc822"
            }
            startActivity(Intent.createChooser(intent, "Выберете почтовый клиент :"))
        }

        openReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", "Славные парни")
                putExtra("year", "2016")
                putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
            }
            startActivity(intent)
        }
    }

}
