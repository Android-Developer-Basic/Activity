package otus.gpb.homework.activities.sender

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding
import java.util.*


class SenderActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySenderBinding

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySenderBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.sendEmail.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("idzayu@mail.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Проверка интента")
            intent.putExtra(
                Intent.EXTRA_TEXT, "Вы получили тестовое сообщение, стоит порадоваться:)"
            );

            startActivity(intent)

        }


        binding.openReceiver.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("otus.homework.receiver")
            intent.type = "text/plain"
            intent.putExtra("title:", "Славные парни")
            intent.putExtra("year:", "2016")
            intent.putExtra(
                "description:",
                "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.\n"
            )
            startActivity(intent)
        }

        binding.toGoogleMaps.setOnClickListener {
            val uri: String = java.lang.String.format(
                Locale.ENGLISH, "https://www.google.com/maps/search/?api=1&query=Рестораны"
            )
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }

}