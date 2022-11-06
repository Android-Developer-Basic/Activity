package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity(R.layout.activity_sender) {
    override fun onStart(){
        super.onStart()
        setContentView(R.layout.activity_sender)
        findViewById<Button>(R.id.buttonToGoogleMaps)
            .setOnClickListener {
                startActivity(Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:?q=поесть")))
            }

        findViewById<Button>(R.id.buttonSendEmail)
            .setOnClickListener {
                startActivity(Intent().setAction(Intent.ACTION_SENDTO)
                .apply{
                        data = Uri.parse("mailto:android@otus.ru")
                        putExtra(Intent.EXTRA_SUBJECT,"Тему придумал сам")
                        putExtra(Intent.EXTRA_TEXT, "Содержание тоже придумал сам")
                     }
                )
            }
        findViewById<Button>(R.id.buttonOpenReceiver)
            .setOnClickListener {
                val movieFirst = Payload(
                    title = "Славные парни",
                    year = "2016",
                    description = "Что бывает, когда напарником брутального костолома " +
                            "становится субтильный лопух? Наемный охранник Джексон Хили и " +
                            "частный детектив Холланд Марч вынуждены работать в паре, " +
                            "чтобы распутать плевое дело о пропавшей девушке, которое " +
                            "оборачивается преступлением века. Смогут ли парни разгадать " +
                            "сложный ребус, если у каждого из них – свои, весьма индивидуальные " +
                            "методы.\n"
                )

                startActivity(
                    Intent()
                        .apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                addCategory(Intent.CATEGORY_DEFAULT)
                                putExtra("title", movieFirst.title)
                                putExtra("year", movieFirst.year)
                                putExtra("description", movieFirst.description)
                                }
                            )
            }
    }
}