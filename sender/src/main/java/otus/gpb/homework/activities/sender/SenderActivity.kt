package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.logging.XMLFormatter

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val map = findViewById<Button>(R.id.map)

        map.setOnClickListener {

            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)

        }


        val email = findViewById<Button>(R.id.email)

        email.setOnClickListener {

            try {
                val addressee = Uri.parse("mailto:android@otus.ru")
                val sendIntent = Intent(Intent.ACTION_SENDTO, addressee)
                startActivity(sendIntent)
            } catch (notFoundException: ActivityNotFoundException) {

            }

        }

        val open = findViewById<Button>(R.id.open)

        open.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND).apply {

                putExtra("title", "Славные парни")
                putExtra("year", "2016")
                putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? " +
                        "Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, " +
                        "чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. " +
                        "Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")

                type = "text/plain"


            }

            startActivity(intent)


        }

    }
}

