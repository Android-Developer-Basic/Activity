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

        val button2GMaps = findViewById<Button>(R.id.button_2GMaps)
        button2GMaps.setOnClickListener {
            val restaurantURI = Uri.parse("geo:0,0?q=restaurants")
            val intent = Intent(Intent.ACTION_VIEW, restaurantURI).apply {
                setPackage("com.google.android.apps.maps")
            }
            try {
                startActivity(intent)
            }
            catch (e: ActivityNotFoundException)
            {
                Toast.makeText(this, "Google Maps not found", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonSendEmail = findViewById<Button>(R.id.button_SendEmail)
        buttonSendEmail.setOnClickListener {
            val emailURI = Uri.parse("mailto:android@otus.ru")
            val intent = Intent(Intent.ACTION_SENDTO, emailURI).apply {
                putExtra(Intent.EXTRA_SUBJECT, "Hello")
                putExtra(Intent.EXTRA_TEXT, "Hello, people!")
            }
            try {
                startActivity(intent)
            }
            catch (e: ActivityNotFoundException)
            {
                Toast.makeText(this, "You mail not sent", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonOpenReceiver = findViewById<Button>(R.id.button_OpenReceiver)
        buttonOpenReceiver.setOnClickListener {

            val b = Bundle()
            b.putString("title", "Интерстеллар")
            b.putString("year", "2014")
            b.putString("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество" +
                    " к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину" +
                    " (которая предположительно соединяет области пространства-времени через большое расстояние)" +
                    " в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека" +
                    " и найти планету с подходящими для человечества условиями.")

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtras(b)
            }
            try {
                startActivity(intent)
            }
            catch (e: ActivityNotFoundException)
            {
                Toast.makeText(this, "Do not open Receiver", Toast.LENGTH_SHORT).show()
            }
        }
    }
}