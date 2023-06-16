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
        val button = findViewById<Button>(R.id.buttonSecondActivityA)
        button.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=Рестораны")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        val button1 = findViewById<Button>(R.id.buttonSecondActivityB)
        button1.setOnClickListener {
                val uriEmail = Uri.parse("mailto:android@otus.ru")
                val emailIntent = Intent(Intent.ACTION_SENDTO, uriEmail)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                emailIntent.putExtra(Intent.EXTRA_TEXT   , "body of email");
                startActivity(emailIntent)
          }
        val button2 = findViewById<Button>(R.id.buttonSecondActivityC)
        button2.setOnClickListener {
            //val eIntent = Intent("android.intent.action.SEND")
            val eIntent = Intent(Intent.ACTION_SEND)
            eIntent.type = "text/plain"
            eIntent.addCategory(Intent.CATEGORY_DEFAULT)
            //eIntent.setType("text/plain")
            //eIntent.addCategory("Category.DEFAULT")
            eIntent.putExtra("title", "Интерстеллар");
            eIntent.putExtra("year"   , "2014");
            eIntent.putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.");
            startActivity(eIntent)
         }
       }
}