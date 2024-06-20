package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
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

        findViewById<Button>(R.id.buttonOpenMaps).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${"Рестораны"}"))
                intent.setPackage("com.google.android.apps.maps")

                startActivity(intent)
            } catch (exception: ActivityNotFoundException) {
                Toast.makeText(this, "Устройство не поддерживает карты", Toast.LENGTH_SHORT)
            }
        }
        findViewById<Button>(R.id.buttonOpenEmail).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:android@otus.ru?subject=Action Title&body=Action Text")
                }
                startActivity(intent)
            } catch (exception: ActivityNotFoundException) {
                Toast.makeText(this, "Устройство не поддерживает отправку письма", Toast.LENGTH_SHORT)
            }
        }
        findViewById<Button>(R.id.buttonOpenReceiver).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.putExtra("title", "Интерстеллар")
                intent.putExtra("year", "2014")
                intent.putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
                startActivity(intent)
            } catch (exception: ActivityNotFoundException) {
                Toast.makeText(this, "Устройство не поддерживает отправку", Toast.LENGTH_SHORT)
            }
        }
    }
}