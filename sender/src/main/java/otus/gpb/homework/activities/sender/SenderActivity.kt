package otus.gpb.homework.activities.sender

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
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

        val googleButton = findViewById<Button>(R.id.googleButton)

        val emailButton = findViewById<Button>(R.id.emailButton)

        val recieverButton = findViewById<Button>(R.id.recieverButton)

        googleButton.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.google.android.apps.maps")

            if (intent != null) {
                intent.action = Intent.ACTION_VIEW
                intent.data = "geo:0,0?q=Рестораны".toUri()
                startActivity(intent)
            } else {
                Toast.makeText(this, "Google Maps недоступен", Toast.LENGTH_SHORT).show()
            }


        }


        emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, "mailto:android@otus.ru".toUri())
            intent.putExtra(Intent.EXTRA_SUBJECT, "Вопрос по домашнему заданию")
            intent.putExtra(Intent.EXTRA_TEXT, "Здравствуйте!")
            startActivity(intent)
        }

        recieverButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            val extras = Bundle().apply {
                putString("title", "Интерстеллар")
                putString("year", "2014")
                putString("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
            }
            intent.putExtras(extras)
            startActivity(intent)
        }
    }
}