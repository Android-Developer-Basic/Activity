package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.button).setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=Рестораны")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:android@otus.ru?subject=Тема письма&body=Текст письма")
            )
            startActivity(intent)
        }

        findViewById<Button>(R.id.button3).setOnClickListener {

            val rand = (Date().time%2).toString().last()
            val title:String
            val year:String
            val description:String
            if(rand =='0') {
                title = "Интерстеллар"
                year = "2014"
                description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
            }
            else {
                title = "Славные парни"
                year = "2016"
                description = "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
            }

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/plain"
                putExtra("title", title)
                putExtra("year", year)
                putExtra("description", description)
            }

            startActivity(intent)
        }
    }
}
