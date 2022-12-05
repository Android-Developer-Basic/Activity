package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale.Category

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:60.080528,30.346742?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            composeEmail("android@otus.ru", "activity#2", "Email have sent!!!")
        }

        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            myfilms()
        }

    }

    fun composeEmail(addresses: String, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf (addresses))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        startActivity(intent)

    }

    fun myfilms () {
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

