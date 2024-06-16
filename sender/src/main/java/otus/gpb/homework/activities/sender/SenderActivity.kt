package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToGoogleMaps.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=Рестораны")
            ).setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        binding.btnSendEmail.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:android@otus.ru")
            ).putExtra(Intent.EXTRA_SUBJECT, "Тема письма")
                .putExtra(Intent.EXTRA_TEXT, "Текст письма")
            startActivity(intent)
        }

        binding.btnOpenReceiver.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/plain"
                intent.putExtra(TITLE_KEY, "Славные парни")
                intent.putExtra(YEAR_KEY, "2016")
                intent.putExtra(
                    DESCRIPTION_KEY,
                    "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
                )
            }
            startActivity(intent)
        }
    }

    companion object {
        const val TITLE_KEY = "title key"
        const val YEAR_KEY = "year key"
        const val DESCRIPTION_KEY = "description key"
    }
}