package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        val buttonToGoogleMaps: Button = findViewById(R.id.button)
        buttonToGoogleMaps.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:55.754283,37.62002?q=restaurants")
            ).setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (e: Exception) {
            }
        }
        val buttonSendEmail: Button = findViewById(R.id.button2)
        buttonSendEmail.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:android@otus.ru?subject=Homework&body=Hello, this is my homework Activity_2")
            )
            try {
                startActivity(intent)
            } catch (e: Exception) {
            }
        }

        val buttonOpenReceiver: Button = findViewById(R.id.button3)

        buttonOpenReceiver.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", "Интерстеллар")
                putExtra("year", "2014")
                putExtra(
                    "description",
                    "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
                )
                .setPackage("otus.gpb.homework.activities.receiver")
            }

            try {
                startActivity(intent)
            } catch (e: Exception) {
            }
        }
    }
}
