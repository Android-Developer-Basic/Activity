package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R

const val KEY_TITLE = "key_title"
const val KEY_YEAR = "key_year"
const val KEY_DESCRIPTION = "key_description"

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

        findViewById<Button>(R.id.buttonToGoogleMaps).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("geo:55.846275, 37.360780?q=restaurant"))
                    .setPackage("com.google.android.apps.maps")
            )
        }

        findViewById<Button>(R.id.buttonSendEmail).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("mailto: android@otus.ru")))
        }

        findViewById<Button>(R.id.buttonOpenReceiver).setOnClickListener {
            startActivity(Intent().apply {
                action = Intent.ACTION_SEND
                addCategory(Intent.CATEGORY_DEFAULT)
                setType("text/plain")

                putExtra(KEY_TITLE, "Славные парни")
                putExtra(KEY_YEAR, "2016")
                putExtra(KEY_DESCRIPTION,
                    "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
                )
            }
            )
        }
    }
}