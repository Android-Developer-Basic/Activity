package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.b_maps).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)

        }

        findViewById<Button>(R.id.b_send).setOnClickListener {
            try {
                val sendIntentUri = Uri.parse("mailto:")
                val sendIntent = Intent(Intent.ACTION_SENDTO, sendIntentUri)
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Ты читал Курта Воннегута")
                sendIntent.putExtra(Intent.EXTRA_TEXT, "\"Колыбель для кошки\"?")
                sendIntent.putExtra(Intent.EXTRA_EMAIL, "android@otus.ru")
                startActivity(sendIntent)

            }
            catch (exception: ActivityNotFoundException) {
                Toast.makeText(this, "Нечем отправить письмо", Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.b_openReceiver).setOnClickListener {

            try {
                val recAct = Intent(Intent.ACTION_SEND)
                recAct.addCategory(Intent.CATEGORY_DEFAULT)
                recAct.type = "text/plain"

                recAct.putExtra("title", "Славные парни")
                recAct.putExtra("year", "2016")
                recAct.putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
                )

                startActivity(recAct)

            }
            catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG).show()
            }
        }
    }
}