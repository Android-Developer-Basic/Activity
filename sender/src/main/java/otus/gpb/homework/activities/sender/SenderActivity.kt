package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast

class SenderActivity : AppCompatActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        findViewById<View>(R.id.toGoogleMap).setOnClickListener {
            onClick(it)
        }
        findViewById<View>(R.id.sendEmail).setOnClickListener {
            onClick(it)
        }
        findViewById<View>(R.id.openReciever).setOnClickListener {
            onClick(it)
        }

    }

    private fun executeIntent(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No Activity found for that action",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun openGoogleMaps(param: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${param}")).apply {
            setPackage("com.google.android.apps.maps")
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            executeIntent(intent)
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            putExtra(Intent.EXTRA_SUBJECT, "Home work")
            putExtra(Intent.EXTRA_TEXT, "Home work Activity 2")
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            executeIntent(intent)
        }
    }

    private fun openReceiver() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra("title", "Славные парни")
            putExtra("year", "2016")
            putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
        }
        executeIntent(intent)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.toGoogleMap -> openGoogleMaps("Рестораны")
            R.id.sendEmail -> sendEmail()
            R.id.openReciever -> openReceiver()
            else -> {
                Toast.makeText(
                    this,
                    "No handler for that element",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}