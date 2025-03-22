package otus.gpb.homework.activities.sender

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R
import androidx.core.net.toUri

const val PLD_NAME = "title"
const val PLD_YEAR = "year"
const val PLD_DESC = "description"

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

        val btToGmaps = findViewById<Button>(R.id.bt_to_gmaps)
        btToGmaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "geo:0,0?q=restaurant".toUri())
                .setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        val btSendEmail = findViewById<Button>(R.id.bt_send_email)
        btSendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, "mailto:android@otus.ru".toUri())
            intent.putExtra(Intent.EXTRA_SUBJECT, "test")
            intent.putExtra(Intent.EXTRA_TEXT, "test")
            startActivity(intent)
        }

        val btOpenReceiver = findViewById<Button>(R.id.bt_open_rcvr)
        btOpenReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
            }
            val payload  = Payload(
                "Славные парни",
                "2016",
                "Что бывает, когда напарником брутального костолома становится" +
                        " субтильный лопух? Наемный охранник Джексон Хили и частный детектив" +
                        " Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о" +
                        " пропавшей девушке, которое оборачивается преступлением века. Смогут ли" +
                        " парни разгадать сложный ребус, если у каждого из них – свои, весьма" +
                        " индивидуальные методы.")
            /**val payload2  = Payload(
                "Интерстеллар",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к" +
                        " продовольственному кризису, коллектив исследователей и учёных отправляется" +
                        " сквозь червоточину (которая предположительно соединяет области " +
                        "пространства-времени через большое расстояние) в путешествие, чтобы " +
                        "превзойти прежние ограничения для космических путешествий человека и " +
                        "найти планету с подходящими для человечества условиями.")
            */
            intent.putExtra(PLD_NAME, payload.title)
            intent.putExtra(PLD_YEAR, payload.year)
            intent.putExtra(PLD_DESC, payload.description)
            startActivity(intent)
        }
    }
}