package otus.gpb.homework.activities.sender

import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {

    private lateinit var buttonGoogle:Button
    private lateinit var buttonMail:Button
    private lateinit var buttonReceiver:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        buttonGoogle = findViewById(R.id.openGoogleMaps)
        buttonMail = findViewById(R.id.openSendMail)
        buttonReceiver = findViewById(R.id.openReceiver)

        buttonGoogle.setOnClickListener(){
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        buttonMail.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto:"))
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "message")

            startActivity(intent)
        }
        buttonReceiver.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.addCategory(CATEGORY_DEFAULT)
            intent.putExtra("title", "Славные парни")
            intent.putExtra("year", "2016")
            intent.putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
            startActivity(intent)
        }
    }
}