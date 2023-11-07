package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {

    var title = "Славные парни"
    var year = "2016"
    var description = "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."

    init {
        val isSecond = true
        if(isSecond){
            title = "Интерстеллар"
            year = "2014"
            description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.toGoogleMap).setOnClickListener {
            val geoUri = Uri.parse("geo:55.8,37,7223?z=9.9&q=restaurant")
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(intent)
        }

        findViewById<Button>(R.id.sendEmail).setOnClickListener {
            val emailUri =
                Uri.parse("mailto:android@otus.ru?subject=My%20subject&body=Good%20morning.")
            val intent = Intent(Intent.ACTION_VIEW, emailUri)
            startActivity(intent)
        }

        findViewById<Button>(R.id.openReceiver).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra("title", title)
                putExtra("year", year)
                putExtra("description", description)
            }

            intent.type = "text/plain"
            intent.addCategory(Intent.CATEGORY_DEFAULT)

            startActivity(intent)
        }
    }
}