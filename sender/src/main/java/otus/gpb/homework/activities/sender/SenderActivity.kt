package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SenderActivity : AppCompatActivity() {

    lateinit var openGoogleMapBTN: Button
    lateinit var sendEmailBTN: Button
    lateinit var openReceiverBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        openGoogleMapBTN = findViewById(R.id.openGoogleMapBTN)
        sendEmailBTN = findViewById(R.id.sendEmailBTN)
        openReceiverBTN = findViewById(R.id.openReceiverBTN)


        openGoogleMapBTN.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=Рестораны?z=17"))
                    .setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        sendEmailBTN.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:android@otus.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "homework")
            intent.putExtra(Intent.EXTRA_TEXT, "Home work is done")
            startActivity(intent)
        }

        openReceiverBTN.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            .addCategory(Intent.CATEGORY_DEFAULT)
            .putExtra("title", "Interstellar")
            .putExtra("year", "2014")
            .putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису,\n" +
                    "        коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени\n" +
                    "        через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с\n" +
                    "        подходящими для человечества условиями.")

            startActivity(intent)
        }
    }
}