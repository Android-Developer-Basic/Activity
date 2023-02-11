package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

val payload = Payload(
    "Славные парни",
    "2016",
    "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
)

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val mapsButton = findViewById<Button>(R.id.toMaps)
        val mailButton = findViewById<Button>(R.id.toMail)
        val receiverButton = findViewById<Button>(R.id.toReceiver)

        mapsButton.setOnClickListener { onMapsButtonClickListener("рестораны") }
        mailButton.setOnClickListener { onMailButtonClickListener() }
        receiverButton.setOnClickListener { onReceiverButtonClickListener() }
    }

    private fun onMapsButtonClickListener(query: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.mapsxxx")
        start(mapIntent, "Не установлены Гугл карты")
    }

    private fun onMailButtonClickListener() {
        val uri = Uri.parse("mailto:android@otus.ru?subject=тема&body=Ну,%20привет")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        start(intent, "Не установлена почта")
    }

    private fun onReceiverButtonClickListener() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra("title", payload.title)
            putExtra("year", payload.year)
            putExtra("description", payload.description)
        }
        start(intent, "Не установлен ресивер")
    }

    private fun start(intent: Intent, errorMessage: String) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            Log.d("SENDER log", "$errorMessage: ActivityNotFoundException")
        }
    }
}