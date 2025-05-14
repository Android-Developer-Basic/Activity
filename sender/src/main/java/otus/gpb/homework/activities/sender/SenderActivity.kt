package otus.gpb.homework.activities.sender

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


const val KEY_PAYLOAD = "key_payload"

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fun SendIntent(intent : Intent) {
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Не удалось обработать intent", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.button1).setOnClickListener {
            val uri = Uri.parse("geo:55.755864,37.617698?q=restaurants")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            SendIntent(intent)
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:android@otus.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Заголовок - ДЗ Activity 2")
            intent.putExtra(Intent.EXTRA_TEXT,"Текст - ДЗ Activity 2")
            SendIntent(intent)
        }

        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            val pl = otus.gpb.homework.activities.mylibrary2.Payload(
                "Интерстеллар",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
            )
            val pl2 = otus.gpb.homework.activities.mylibrary2.Payload(
                "Славные парни",
                "2016",
                "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
            )
            intent.putExtras(
                Bundle().apply {
                    putSerializable(KEY_PAYLOAD, pl2)
                }
            )
            SendIntent(intent)
        }
    }
}