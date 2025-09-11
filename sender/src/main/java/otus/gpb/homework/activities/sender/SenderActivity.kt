package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R
import kotlin.Exception


class SenderActivity : AppCompatActivity() {
    private var buttonMap: Button? = null
    private var buttonMail: Button? = null
    private var buttonReceiver: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        buttonMap = findViewById(R.id.button)
        buttonMail = findViewById(R.id.button2)
        buttonReceiver = findViewById(R.id.button3)

        buttonMap?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=restaurants")).setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Не удалось открыть карту",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        buttonMail?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:android@otus.ru?subject=\"Отзыв о курсе обучения\"&body=\"Курс очень понравился. Получил много полезной информации и навыков.\""))
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Не удалось открыть почтовый клиент",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        buttonReceiver?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("title", "Interstellar")
            intent.putExtra("year", "2014")
            intent.putExtra("description",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
            )
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Не удалось открыть Receiver",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}