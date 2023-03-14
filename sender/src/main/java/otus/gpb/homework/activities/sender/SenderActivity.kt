package otus.gpb.homework.activities.sender

import android.app.Notification
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonMaps = findViewById<Button>(R.id.gmapsButton)
        buttonMaps.setOnClickListener{
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }
        }

        val buttonSendEmail = findViewById<Button>(R.id.send_email)
        buttonSendEmail.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("mobile5320@yandex.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
            intent.putExtra(Intent.EXTRA_TEXT, "This is a test mail")
            try {
                startActivity(Intent.createChooser(intent, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "There are no email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val buttonReceiver = findViewById<Button>(R.id.open_receiver)
        buttonReceiver.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)

            intent.putExtra("title", "Интерстеллар")
            intent.putExtra("year", "2014")
            intent.putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")

            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "Activity not found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}