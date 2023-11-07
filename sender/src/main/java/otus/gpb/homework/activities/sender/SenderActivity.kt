package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity(R.layout.activity_sender) {
  override fun onStart() {
    super.onStart()

    findViewById<Button>(R.id.btnToGoogleMaps).setOnClickListener { toGoogleMaps() }
    findViewById<Button>(R.id.btnSendEmail).setOnClickListener { sendEmail() }
    findViewById<Button>(R.id.btnOpenReceiver).setOnClickListener { openReceiver() }
  }

  private fun toGoogleMaps() {
    val coords = "0,0"
    val search = "Рестораны"
    val intent = Intent(
      Intent.ACTION_VIEW,
      Uri.parse("geo:$coords?q=$search")
    )
    intent.setPackage("com.google.android.apps.maps")

    startActivity(intent)
  }

  private fun sendEmail() {
    val destinationEmail = "android@otus.ru"
    val subject = "Hello otus"
    val body = "Nice to meat you!"
    val intent = Intent(
      Intent.ACTION_SENDTO,
      Uri.parse("mailto:$destinationEmail?subject=$subject&body=$body"),
    )

    startActivity(intent)
  }

  private fun openReceiver() {
    val payload = Payload(
      title = "Интерстеллар",
      year = "2014",
      description = "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.",
    )
    val intent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/plain"
      addCategory(Intent.CATEGORY_DEFAULT)
      putExtra("payload", Bundle().apply {
        putString("title", payload.title)
        putString("year", payload.year)
        putString("description", payload.description)
      })
    }

    startActivity(intent)
  }
}