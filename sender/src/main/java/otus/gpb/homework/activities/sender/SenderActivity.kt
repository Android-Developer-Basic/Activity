package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class SenderActivity : AppCompatActivity(R.layout.activity_sender) {
    override fun onStart() {
        super.onStart()


        val googleBtn = findViewById<Button>(R.id.button_google)
        val emailBtn = findViewById<Button>(R.id.button_email)
        val receiverBtn = findViewById<Button>(R.id.button_receiver)

        googleBtn.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:55.751244,37.618423?q=restaurants")
                ).setPackage("com.google.android.apps.maps")

            startActivity(intent)
        }

        emailBtn.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto: android@otus.ru?subject=Приветствие&body=Привет!")
            )

            startActivity(emailIntent)
        }

        receiverBtn.setOnClickListener {
            val interstellar = Payload(
                "Интерстеллар",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
            )

            val intent = Intent(Intent.ACTION_SEND)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .setType("text/plain")
                .putExtra("title", interstellar.title)
                .putExtra("year", interstellar.year)
                .putExtra("description", interstellar.description)

            startActivity(intent)
        }


    }


}