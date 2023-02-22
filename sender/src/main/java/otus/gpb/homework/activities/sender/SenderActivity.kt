package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val gmaps = findViewById<Button>(R.id.to_gmaps)
        val sendEmail = findViewById<Button>(R.id.send_email)
        val openReceiver = findViewById<Button>(R.id.open_receiver)

        gmaps.setOnClickListener {
            val uri = Uri.parse("geo:0.0?q=Рестораны")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "На устройстве не установлен Google Maps!", Toast.LENGTH_LONG).show()
            }
        }

        sendEmail.setOnClickListener {
            val uri = Uri.parse("mailto:" + "android@otus.ru" + "?subject=" + "Тест" + "&body=" + "Тестовое сообщение")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "На устройстве не установлен почтовый клиент!", Toast.LENGTH_LONG).show()
            }
        }

        openReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .addCategory(Intent.CATEGORY_DEFAULT)
                .apply {
                    putExtra("title", "Интерстеллар")
                    putExtra("year", "2014")
                    putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
                }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }
}