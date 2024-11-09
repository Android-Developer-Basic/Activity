package otus.gpb.homework.activities.sender

import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.buttonMap).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=restaurant")).setPackage("com.google.android.apps.maps")
            )
        }

        findViewById<Button>(R.id.buttonMail).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    val addresses = arrayOf("android@otus.tu")
                    putExtra(Intent.EXTRA_EMAIL, addresses)
                    putExtra(Intent.EXTRA_SUBJECT, "Homework 08")
                    putExtra(Intent.EXTRA_TEXT, "Hello from my homework app")
                }
            )
        }

        findViewById<Button>(R.id.buttonSendPayload).setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    addCategory(CATEGORY_DEFAULT)
                    putExtra(KEY_TITLE, "Интерстеллар")
                    putExtra(KEY_YEAR, "2014")
                    putExtra(KEY_DESC, "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, " +
                            "коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области " +
                            "пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических " +
                            "путешествий человека и найти планету с подходящими для человечества условиями.")
                }.setPackage("otus.gpb.homework.activities.receiver")
            )
        }
    }

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_DESC = "desc"
        const val KEY_YEAR = "year"
    }
}