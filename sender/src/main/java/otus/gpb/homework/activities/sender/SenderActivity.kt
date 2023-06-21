package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {

    val binding: ActivitySenderBinding by lazy { ActivitySenderBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonGoogleMaps.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?q=Рестораны")
                )
            )
        }
        binding.buttonSendEmail.setOnClickListener {//Outlook поддерживает Extras, Gmail - нет
            try {
                startActivity(
                    Intent(Intent.ACTION_SENDTO).apply {
                        putExtra(Intent.EXTRA_SUBJECT, "Activity 2 HW")
                        putExtra(Intent.EXTRA_TEXT, "Письмо из приложения Sender")
                        setData(Uri.parse("mailto:android@otus.ru"))
                    }
                )
            }catch (exception: ActivityNotFoundException){
                Toast.makeText(applicationContext, "Нет приложений для отправки писем=(", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonOpenReceiver.setOnClickListener {
            try {
                startActivity(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra("title", "Славные парни")
                        putExtra("year", "2016")
                        putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
                        intent.addCategory(Intent.CATEGORY_DEFAULT)
                    }
                )
            }catch (exception: ActivityNotFoundException){
                Toast.makeText(applicationContext, "Нет приложений для отправки писем=(", Toast.LENGTH_SHORT).show()
            }
        }
    }
}