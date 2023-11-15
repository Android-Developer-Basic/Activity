package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class SenderActivity : AppCompatActivity(R.layout.activity_sender) {

    private fun handleErrorString(errorText: String? = null) {
        val id = findViewById<TextView>(R.id.error_text)
        if (errorText.isNullOrEmpty()) {
            id.visibility=TextView.INVISIBLE
        } else {
            id.text=errorText
            id.visibility=TextView.VISIBLE
        }
    }

    private fun runActivity(intent: Intent?) {
        try {
            startActivity(intent)
            handleErrorString()
        } catch (e: ActivityNotFoundException) {
            handleErrorString("Activity not found")
            Log.d("MovieInfo",e.toString())
        } catch (e: Exception) {
            handleErrorString("Activity launch error ${e.toString()}")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toGmaps = findViewById<Button>(R.id.to_google_maps)
        toGmaps.setOnClickListener {
            val query="рестораны"
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo: ?q=$query")
            ).setPackage("com.google.android.apps.maps")
            runActivity(intent)
        }

        val sendEmail = findViewById<Button>(R.id.send_email)
        val subject = "Тема"
        val text = "Текст"
        val address="android@otus.ru"
        sendEmail?.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("mailto:$address?subject=$subject&body=$text")
            )
            runActivity(intent)
        }

        val openReceiver = findViewById<Button>(R.id.open_receiver)
        openReceiver?.setOnClickListener {
            val movieInfo = Payload(
                "Славные парни",
                "2016",
                "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.",
                "niceguys")

            val intent = Intent(
                Intent.ACTION_SEND
            ).apply {
                setType("text/plain")
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", movieInfo.title)
                putExtra("year", movieInfo.year)
                putExtra("description", movieInfo.description)
                putExtra("image", movieInfo.image)
            }
            runActivity(intent)
        }
    }



}