package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.open_receiver_btn).setOnClickListener { openReceiver() }
        findViewById<Button>(R.id.send_email_btn).setOnClickListener { sendEmail() }
        findViewById<Button>(R.id.to_google_maps_btn).setOnClickListener { openGoogleMaps("restaurants") }
    }

    private fun openGoogleMaps(searchParam: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setPackage("com.google.android.apps.maps")
            data = Uri.parse("geo:0,0?q=$searchParam")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            exceptionHandler(R.id.to_google_maps_btn)
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:android@otus.ru?subject=azaza&body=azaza")
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            exceptionHandler(R.id.send_email_btn)
        }
    }

    private fun openReceiver() {
        val data = Payload(
            "Славные парни",
            "2016",
            "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(payloadTitle, data.title)
            putExtra(payloadYear, data.year)
            putExtra(payloadDescription, data.description)
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            exceptionHandler(R.id.open_receiver_btn)
        }
    }

    private fun exceptionHandler(resourceId: Int) {
        findViewById<Button>(resourceId).visibility = View.INVISIBLE
        Toast.makeText(this, "Sorry, you dont have any app to handle it", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val payloadTitle: String = "title"
        const val payloadYear: String = "year"
        const val payloadDescription: String = "description"
    }
}