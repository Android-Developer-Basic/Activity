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

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.to_maps).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:53.099077, 49.948756?q=Рестораны"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        findViewById<Button>(R.id.send_email).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:android@otus.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "На твой телефон пришло новое сообщение...")
            intent.putExtra(Intent.EXTRA_TEXT, "Посмотри, вдруг там что-то важное...")
            startActivity(intent)
        }

        findViewById<Button>(R.id.open_receiver).setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                val payload = Payload(
                    "Славные парни",
                    "2016",
                    "Что бывает, когда напарником брутального костолома становится " +
                            "субтильный лопух? Наемный охранник Джексон Хили и частный детектив " +
                            "Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело " +
                            "о пропавшей девушке, которое оборачивается преступлением века. " +
                            "Смогут ли парни разгадать сложный ребус, если у каждого из них – " +
                            "свои, весьма индивидуальные методы.\n")
                putExtra("title", payload.title)
                putExtra("year", payload.year)
                putExtra("description", payload.description)
            }
            startActivity(intent)
        }
    }
}