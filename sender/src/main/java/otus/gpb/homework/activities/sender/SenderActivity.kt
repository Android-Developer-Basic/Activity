package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.toGoogleMapsButton).setOnClickListener {
            openGoogleMaps()
        }
        findViewById<Button>(R.id.sendEmailButton).setOnClickListener {
            sendEmail()
        }
        findViewById<Button>(R.id.openReceiverButton).setOnClickListener {
            openReceiver()
        }
    }

    private fun openGoogleMaps() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Рестораны")).apply {
            setClassName(GOOGLE_MAPS_PACKAGE, GOOGLE_MAPS_ACTIVITY)
        }
        startActivity(intent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:android@otus.ru")
            putExtra(Intent.EXTRA_SUBJECT, "Домашнее задание по Activity")
            putExtra(Intent.EXTRA_TEXT, "Здравствуйте! Отправляю письмо из SenderActivity.")
        }
        startActivity(intent)
    }

    private fun openReceiver() {
        val payload = Payload(
            title = "Интерстеллар",
            year = "2014",
            description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(EXTRA_TITLE, payload.title)
            putExtra(EXTRA_YEAR, payload.year)
            putExtra(EXTRA_DESCRIPTION, payload.description)
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_YEAR = "year"
        const val EXTRA_DESCRIPTION = "description"

        private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"
        private const val GOOGLE_MAPS_ACTIVITY = "com.google.android.maps.MapsActivity"
    }
}
