package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {

    private val listFilm = listOf(
        FilmDTO(
            "Славные парни",
            "2016",
            "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
        ),
        FilmDTO(
            "Интерстеллар",
            "2014",
            "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonToGoogleMap = findViewById<Button>(R.id.to_google_maps)
        val buttonSendEmail = findViewById<Button>(R.id.send_email)
        val buttonOpenReceiver = findViewById<Button>(R.id.open_receiver)

        buttonToGoogleMap.setOnClickListener {
            val uri = Uri.parse("geo:0.0?q=Рестораны")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch(e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "На устройстве не обнаружено подходящего приложения!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        buttonSendEmail.setOnClickListener {
            val uri =
                Uri.parse("mailto: android@otus.ru ?subject= Test massage &body= Hello. This is test post by android intent.")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "На устройстве не обнаружено установленного почтового клиента!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        buttonOpenReceiver.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .addCategory(Intent.CATEGORY_DEFAULT)
                .apply {
                    val film = listFilm.random()
                    putExtra("title", film.title)
                    putExtra("year", film.year)
                    putExtra("description", film.description)
                }
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "(!) Упс. Что-то пошло не так :(",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}