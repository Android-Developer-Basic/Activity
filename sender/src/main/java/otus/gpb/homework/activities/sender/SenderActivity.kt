package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dto.MovieDTO


class SenderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonMaps = findViewById<Button>(R.id.bttogooglemaps)
        val buttonEmail = findViewById<Button>(R.id.btsendmail)
        val buttonReceiver = findViewById<Button>(R.id.btopenreceiver)

        buttonMaps.setOnClickListener {
            try {
                val gmmIntentUri = Uri.parse("geo:0,0?q=Рестораны")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                startActivity(mapIntent)
            } catch (exception: ActivityNotFoundException) {
                Toast.makeText(this, "На вашем телефоне нет Google Maps", Toast.LENGTH_LONG).show()
            }
        }

        buttonEmail.setOnClickListener {
            try {
                val mailIntentUri = Uri.parse( "mailto:android@otus.ru")
                val mailIntent  = Intent(Intent.ACTION_SENDTO, mailIntentUri)
                mailIntent.putExtra(Intent.EXTRA_SUBJECT,"Homework Activity #2")
                mailIntent.putExtra(Intent.EXTRA_TEXT,"Проверка работы вызова Activity почтового приложения через Intent и Action")
                startActivity(mailIntent)
            } catch (exception: ActivityNotFoundException){
                Toast.makeText(this, "На вашем телефоне нет ни одного почтового приложения", Toast.LENGTH_LONG).show()
            }
        }

        buttonReceiver.setOnClickListener {
            try {
                val movieDesDTO = MovieDTO(
                    "Интерстеллар",
                    "2014",
                    "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
                )
                val receiverIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    addCategory("category.DEFAULT")
                    putExtra("dataset", movieDesDTO)
                }
                startActivity(receiverIntent)
            } catch (exception: ActivityNotFoundException) {
                Toast.makeText(this, "Нет обработчика для этого элемента", Toast.LENGTH_LONG).show()
            }
        }
    }
}