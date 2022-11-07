package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySenderBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val title = "Славные парни"
        val year = "2016"
        val description = "Что бывает, когда напарником брутального костолома " +
                "становится субтильный лопух?" +
                " Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены " +
                "работать в паре, чтобы распутать плевое дело о пропавшей девушке, " +
                "которое оборачивается преступлением века. " +
                "Смогут ли парни разгадать сложный ребус, если у каждого из них – свои," +
                " весьма индивидуальные методы."

        binding.btnToGoogleMaps.setOnClickListener {
            val geoUriString = "geo:0,0?q=Moscow+restaurants&z=8"
            val geoUri = Uri.parse(geoUriString)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = geoUri
                setPackage("com.google.android.apps.maps")
            }

            startActivity(intent)
        }

        binding.btnSendEmail.setOnClickListener {
            val email = arrayOf("android@otus.ru")
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                putExtra(Intent.EXTRA_EMAIL, email)
                putExtra(Intent.EXTRA_SUBJECT, "Homework")
                putExtra(Intent.EXTRA_TEXT, "Some text")
                data = Uri.parse("mailto:")
            }

            startActivity(intent)
        }

        binding.btnOpenReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
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
