package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R
import ru.andrusov.shared.Payload

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.sender_activity)

        findViewById<Button>(R.id.to_google_maps_button).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)
        }

        findViewById<Button>(R.id.send_email_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)

            intent.setData(Uri.parse("mailto:"))

            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Придуманная тема письма")
            intent.putExtra(Intent.EXTRA_TEXT, "Придуманный текст письма")

            startActivity(intent)
        }

        findViewById<Button>(R.id.open_receiver_button).setOnClickListener {
            startActivity(Intent().apply {
                action = Intent.ACTION_SEND
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/plain"

                putExtra("payload", getPayload())
            })
        }
    }

    private fun getPayload() = Payload(
        "Славные парни",
        "2016",
        "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
    )
}
