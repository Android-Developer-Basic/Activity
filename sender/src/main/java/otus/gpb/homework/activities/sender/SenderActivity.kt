package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R
import android.content.Intent.CATEGORY_DEFAULT

class SenderActivity : AppCompatActivity() {

    private var buttonToGoogleMaps: Button? = null
    private var buttonSendMail: Button? = null
    private var buttonOpenReceiver: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)

        buttonToGoogleMaps = findViewById(R.id.buttonToGoogleMaps)
        buttonToGoogleMaps?.setOnClickListener{
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=Ресторан")
            ).setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        buttonSendMail = findViewById(R.id.buttonSendMail)
        buttonSendMail?.setOnClickListener{
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:android@otus.ru?subject=" + Uri.encode("Очень важное письмо"))
            )
            startActivity(intent)
        }

        buttonOpenReceiver = findViewById(R.id.buttonOpenReceiver)
        buttonOpenReceiver?.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setClassName("otus.gpb.homework.activities.receiver", "otus.gpb.homework.activities.receiver.ReceiverActivity")
            intent.type = "text/plan"
            intent.addCategory(CATEGORY_DEFAULT)
            intent.putExtra("title", "Славные парни")
            intent.putExtra("year", "2016")
            intent.putExtra("description",
                "Что бывает, когда напарником брутального костолома " +
                        "становится субтильный лопух? Наемный охранник Джексон " +
                        "Хили и частный детектив Холланд Марч вынуждены работать " +
                        "в паре, чтобы распутать плевое дело о пропавшей девушке, " +
                        "которое оборачивается преступлением века. " +
                        "Смогут ли парни разгадать сложный ребус, " +
                        "если у каждого из них – свои, весьма индивидуальные методы.")
            startActivity(intent)
        }



    }
}