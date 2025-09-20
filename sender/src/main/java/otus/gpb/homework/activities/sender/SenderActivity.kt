package otus.gpb.homework.activities.sender

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
        setContentView(R.layout.sender_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.buttonMaps).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, "geo:0,0?q=restaurants".toUri())
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonEmail).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, "mailto:android@otus.ru".toUri())
            intent.putExtra(Intent.EXTRA_SUBJECT, "some subject")
            intent.putExtra(Intent.EXTRA_TEXT, "some email text")
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonReceiver).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("title", "Интерстеллар")
            intent.putExtra("year", "2014")
            intent.putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
            startActivity(intent)
        }
    }
}