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

        findViewById<Button>(R.id.btnToGoogleMaps).setOnClickListener { funToGoogleMaps() }
        findViewById<Button>(R.id.btnSendEmail).setOnClickListener { funSendEmail() }
        findViewById<Button>(R.id.btnOpenReceiver).setOnClickListener { funOpenReceiver() }
    }

    // По клику на кнопку “To Google Maps”, используя явный Intent вызовите Activity приложения Google Maps.
    // После того как Google Maps поймает ваш Intent, в нем должны отобразиться ближайшие
    // к текущей геолокации места по тэгу “Рестораны”
    private fun funToGoogleMaps() {
        val myLatitude = 47.2291265
        val myLongitude = 39.7117774
        val uriQuery = "ресторан"
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:$myLatitude,$myLongitude?q=$uriQuery")
        ).setPackage("com.google.android.apps.maps") // указваем конкретное приложение
        startActivity(intent)
    }

    //По клику на кнопку “Send Email” отправьте неявный Intent в метод startActivity()
    // Этот Intent должны уметь обработать любые почтовые клиенты(если они реализовали intent-filter согласно контракту).
    // В качестве адресата используйте ящик android@otus.ru, тему и содержание письма придумайте сами.
    private fun funSendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Тема")
        intent.putExtra(Intent.EXTRA_TEXT, "Текст ")
        startActivity(intent)
    }

    // По клику на кнопку “Open Receiver” отправьте неявный Intent со следующими параметрами:
    //   action = Action.SEND
    //   type = “text/plain”
    //   category = Category.DEFAULT
    // В качестве extras отправьте три объекта String. В качестве значений extras
    // используйте любой набор данных из файла payload.txt, который лежит в корне проекта sender
    private fun funOpenReceiver() {
        val bundle = Bundle()
        bundle.putString("title", "Интерстеллар")
        bundle.putString("year", "2014")
        bundle.putString("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису,"+
                "коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени"+
                " через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека"+
                " и найти планету с подходящими для человечества условиями.")
        val intent = Intent(Intent.ACTION_SEND)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.setType("text/plain")
        intent.putExtras(bundle)

        startActivity(intent)
    }
}