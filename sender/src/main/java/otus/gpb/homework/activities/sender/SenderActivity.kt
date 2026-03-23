package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.button_ToGoogleMaps).setOnClickListener {
            try {
                startActivity(openGoogleMaps())
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Ошибка открытия карты",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        findViewById<Button>(R.id.button_SendEmail).setOnClickListener {
            try {
                startActivity(sendMail())
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Ошибка открытия почтового агента",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        findViewById<Button>(R.id.button_OpenReceiver).setOnClickListener {
            try {
                startActivity(sendMessage())
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Ошибка открытия Receiver'a",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun openGoogleMaps() : Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=restaurant"))
            .setPackage("com.google.android.apps.maps")
    }

    fun sendMail() : Intent {
        val uriText = "mailto:android@otus.ru" +
                      "?subject=" + Uri.encode("otus") +
                      "&body=" + Uri.encode("Hello world")
        return Intent(Intent.ACTION_SENDTO, Uri.parse(uriText))
    }

    fun sendMessage() : Intent {
        return Intent(Intent.ACTION_SEND)
        .addCategory(Intent.CATEGORY_DEFAULT)
        .setType("text/plain")
        .putExtra("title", "niceguys")
        .putExtra("year", "2016")
        .putExtra("description",
            "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
        )
    }
}
