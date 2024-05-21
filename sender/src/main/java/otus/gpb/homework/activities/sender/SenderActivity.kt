package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import com.example.mylibrary.Payload
import androidx.appcompat.app.AppCompatActivity

class SenderActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonMaps = findViewById<Button>(R.id.GoogleMaps)
        buttonMaps?.setOnClickListener {
            val intent = Intent (
            Intent.ACTION_VIEW,
            Uri.parse("geo:59.9386,30.3141?q=ресторан")
            ).setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
        val buttonEmail = findViewById<Button>(R.id.SendEmail)
        buttonEmail?.setOnClickListener {
            val intent = Intent (Intent.ACTION_SENDTO)
            intent.data=Uri.parse("mailto:android@otus.ru?subject=Тема письма&body=Текст письма")
            startActivity(intent)
        }
        val buttonReceiver = findViewById<Button>(R.id.OpenReceiver)
        buttonReceiver?.setOnClickListener {
           val intent = openReceiver()
           startActivity(intent)
        }
    }

    private fun openReceiver() = Intent ().apply {
        putExtra("movie", Payload("Славные парни", "2016", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."))
        setAction(Intent.ACTION_SEND)
        setType("text/plane")
        addCategory(Intent.CATEGORY_DEFAULT)
    }
}