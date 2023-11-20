package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SenderActivity : AppCompatActivity() {

    private val data1 = Payload(title = "Славные парни", year ="2016",
        description = "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
    private val data2 = Payload(title = "Интерстеллар", year ="2014",
        description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
    private val data_array = listOf(data1,data2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sender_activity)

        val location = Uri.parse("geo:0,0?q=Ресторан")
        findViewById<Button?>(R.id.btn_google_maps).setOnClickListener {
            intent = Intent( Intent.ACTION_VIEW , location)
                .setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        findViewById<Button?>(R.id.btn_send_email).setOnClickListener {
            intent = Intent( Intent.ACTION_SENDTO,
                Uri.parse("mailto:android@otus.ru"))
            startActivity(intent)
        }

        findViewById<Button?>(R.id.btn_open_receiver).setOnClickListener {
            val data = data_array.random()
            Log.d("Sender - title", data.title)

            intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", data.title)
                putExtra("year", data.year)
                putExtra("description", data.description)
                action = "MyReceiver"    // ДЛЯ ПЕРЕДАЧИ В НАШЕ ПРИЛОЖЕНИЕ
            }
            startActivity(intent)
        }
    }

}