package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class SenderActivity : AppCompatActivity(R.layout.activity_sender) {

    override fun onStart() {
        super.onStart()
        findViewById<Button>(R.id.toGoogleMapsButton).setOnClickListener {
            val intent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("geo:0,0?q=Рестораны"))
                .setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }
        findViewById<Button>(R.id.sendEmailButton).setOnClickListener {
            val intent = Intent()
                .setAction(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:android@otus.ru?subject=subject&body=body"))
            startActivity(intent)
        }
        findViewById<Button>(R.id.openReceiverButton).setOnClickListener {
            val niceGuysPayload = Payload(
                title = "Славные парни",
                year = "2016",
                description = """Что бывает, когда напарником брутального костолома становится 
                    |субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд 
                    |Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей 
                    |девушке, которое оборачивается преступлением века. Смогут ли парни разгадать 
                    |сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."""
                    .trimMargin()
            )
            val interstellarPayload = Payload(
                title = "Интерстеллар",
                year = "2014",
                description = """Когда засуха, пыльные бури и вымирание растений приводят 
                    |человечество к продовольственному кризису, коллектив исследователей и 
                    |учёных отправляется сквозь червоточину (которая предположительно соединяет 
                    |области пространства-времени через большое расстояние) в путешествие, чтобы 
                    |превзойти прежние ограничения для космических путешествий человека и найти 
                    |планету с подходящими для человечества условиями."""
                    .trimMargin()
            )
            val intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .addCategory(Intent.CATEGORY_DEFAULT)
                .putExtra("title", niceGuysPayload.title)
                .putExtra("year", niceGuysPayload.year)
                .putExtra("description", niceGuysPayload.description)
            startActivity(intent)
        }
    }

}