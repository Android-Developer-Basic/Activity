package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)


        findViewById<Button>(R.id.act_a_button_google_maps).setOnClickListener {
            val uri: Uri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            handleError(mapIntent)
        }

        findViewById<Button>(R.id.act_a_button_to_email).setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Я к вам пишу")
                putExtra(Intent.EXTRA_TEXT, "Чего же боле")
            }
            handleError(emailIntent)
        }

        val button3 = findViewById<Button>(R.id.act_a_button_to_open_receiver)
        button3.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/plain"
                putExtra("title", "Славные парни")
                putExtra("year", "2016")
                putExtra(
                    "description",
                    "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
                )
            }
            handleError(sendIntent)
        }
    }

    private fun handleError(intentIntent: Intent) {
        try {
            startActivity(intentIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show()
        }
    }
}


