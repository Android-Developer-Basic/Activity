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

        val button: Button = findViewById<Button>(R.id.toGoogleMaps)
        button.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=рестораны")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps");
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "нет подходящего приложения",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val button2: Button = findViewById(R.id.sendEmail)
        button2.setOnClickListener {
            val uri = Uri.parse(
                StringBuilder().apply {
                    append("mailto:")
                    append("android@otus.ru")
                    append("?subject=")
                    append("классный курс!")
                    append("&body=")
                    append("спасибо всем причастным)))")
                }.let {
                    it.toString()
                }
            )
                //"mailto:" + "android@otus.ru" + "?subject=" + "классный курс!" + "&body=" + "спасибо всем причастным)))")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "нет подходящего приложения",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val button3: Button = findViewById(R.id.openReceiver)
        button3.setOnClickListener {
            val intent = Intent().apply {
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
            startActivity(intent)
        }
    }
}