package otus.gpb.homework.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R
import otus.gpb.homework.activities.sender.Payload

const val KEY_TITLE = "title"
const val KEY_YEAR = "year"
const val KEY_DESCR = "descr"

class SenderActivity : AppCompatActivity() {

    private var btnToGoogleMaps : Button? = null
    private var btnSendEmail    : Button? = null
    private var btnOPenReceiver : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        btnToGoogleMaps = findViewById(R.id.BtnToGoogleMaps)
        btnSendEmail    = findViewById(R.id.BtnSendEmail)
        btnOPenReceiver = findViewById(R.id.BtnOPenReceiver)


        btnToGoogleMaps?.setOnClickListener {
            startActivity(intentOpenMap())
        }

        btnSendEmail?.setOnClickListener {
            startActivity(intentSendEmail())
        }

        btnOPenReceiver?.setOnClickListener {
            try {
                // Code that may throw an exception
                startActivity(intentOpenReceiver())
            } catch (e: Exception) {
                // Code for handling the exception
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun intentOpenMap(): Intent {

        // По клику на кнопку “To Google Maps”, используя явный Intent вызовите Activity приложения Google Maps.
        // После того как Google Maps поймает ваш Intent, в нем должны отобразиться
        // ближайшие к текущей геолокации места по тэгу “Рестораны”

        val uri = "geo:0,0?q=restaurants"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")                           // указываем, что нам нужен именно Google Maps, т.е. делаем Intent явным
        //startActivity(intent)

        return intent
    }

    private fun intentSendEmail(): Intent {

        //По клику на кнопку “Send Email” отправьте неявный Intent в метод startActivity()
        // Этот Intent должны уметь обработать любые почтовые клиенты(если они реализовали intent-filter согласно контракту).
        // В качестве адресата используйте ящик android@otus.ru, тему и содержание письма придумайте сами.

        val email   = "android@otus.ru"
        val subject = "Тема письма: ДЗ Activity #2"
        val body    = "Здесь должно быть содержимое письма"

        val emailIntent1 = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("mailto:$email"))
        emailIntent1.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent1.putExtra(Intent.EXTRA_TEXT, body)

        return emailIntent1
    }

    private fun intentOpenReceiver(): Intent {

        val niceGuys: Payload = Payload(
            title = "Славные парни",
            year = "2016",
            description = "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
        )
        val interstellar: Payload = Payload(
            title = "Интерстеллар",
            year = "2014",
            description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
        )

        val sendPayLoadIntent = Intent().apply {
            action = Intent.ACTION_SEND
            addCategory(Intent.CATEGORY_DEFAULT)
            setType("text/plain")
            putExtra(KEY_TITLE, interstellar.title)
            putExtra(KEY_YEAR, interstellar.year)
            putExtra(KEY_DESCR, interstellar.description)
        }

        return sendPayLoadIntent
    }
}