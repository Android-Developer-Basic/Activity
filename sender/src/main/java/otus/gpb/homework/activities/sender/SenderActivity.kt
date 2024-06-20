package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {

    private var buttonToGoogleMaps: Button? = null
    private var buttonSendEmail: Button? = null
    private var buttonOpenReceiver: Button? = null

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        buttonToGoogleMaps = findViewById(R.id.button_to_google_maps)
        buttonSendEmail = findViewById(R.id.button_send_email)
        buttonOpenReceiver = findViewById(R.id.button_open_receiver)

        buttonToGoogleMaps?.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW, Uri.parse("geo:0.0?q=Рестораны")
            ).setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        buttonSendEmail?.setOnClickListener {
            /*
            ACTION_SENDTO may ignore EXTRA_SUBJECT and EXTRA_TEXT on some devices
            ACTION_SEND may ignore Uri.parse("mailto:") and suggest all the messaging apps instead
            so SEND is used here for subject and body, SENDTO as an e-mail apps selector.
            Alternative way is to provide all the information inside ACTION_SENDTO Uri.parse:
            Uri.parse("mailto:android@otus.ru?subject=Test+Subject&body=Test+text.")
            */
            val intent = Intent(Intent.ACTION_SEND).apply {
                selector = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Test Subject")
                putExtra(Intent.EXTRA_TEXT, "Test text.")
            }
            startActivity(intent)
        }

        buttonOpenReceiver?.setOnClickListener {
            val intent = Intent().apply {
                val movie = getMovies().asSequence().shuffled().first()
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", movie.title)
                putExtra("year", movie.year)
                putExtra("description", movie.description)
            }
            startActivity(intent)
        }
    }
}

private fun getMovies() =
    listOf(
        Payload(
            "Интерстеллар",
            "2014",
            "Когда засуха, пыльные бури и вымирание растений приводят человечество " +
                    "к продовольственному кризису, коллектив исследователей и учёных " +
                    "отправляется сквозь червоточину (которая предположительно соединяет " +
                    "области пространства-времени через большое расстояние) в путешествие, " +
                    "чтобы превзойти прежние ограничения для космических путешествий человека " +
                    "и найти планету с подходящими для человечества условиями."
        ), (Payload(
            "Славные парни",
            "2016",
            "Что бывает, когда напарником брутального костолома становится " +
                    "субтильный лопух? Наемный охранник Джексон Хили и частный детектив " +
                    "Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело " +
                    "о пропавшей девушке, которое оборачивается преступлением века. Смогут " +
                    "ли парни разгадать сложный ребус, если у каждого из них – свои, " +
                    "весьма индивидуальные методы."
        ))
    )