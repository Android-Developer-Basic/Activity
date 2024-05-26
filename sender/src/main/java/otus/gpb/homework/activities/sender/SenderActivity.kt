package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

const val PLD_TITLE = "title"
const val PLD_YEAR = "year"
const val PLD_DESCR = "description"

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val btnShowInMaps: Button = findViewById(R.id.btn_to_maps)
        btnShowInMaps.setOnClickListener {
            startActivity(showInMapsNearBy("restaurants"))
        }

        val btnSendEmail: Button = findViewById(R.id.btn_send_email)
        btnSendEmail.setOnClickListener {
            val intent: Intent = sendEmailTo(
                "android@otus.ru",
                "Android developer.Basic",
                "Hello from student!"
            )
            startActivity(intent)
        }

        val btnOpenReceiver: Button = findViewById(R.id.btn_open_receiver)
        btnOpenReceiver.setOnClickListener {
            val intent: Intent =openReceiver(
                "Terminator 2",
                "1991",
                "Science fiction action film"
            )
            startActivity(intent)
        }
    }

    private fun showInMapsNearBy(tag: String): Intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse("geo:0,0?q=$tag")
        setPackage("com.google.android.apps.maps")
    }

    private fun sendEmailTo(
        email: String,
        subject: String = "No subject",
        text: String = "",
    ): Intent = Intent().apply {
        action = Intent.ACTION_SENDTO
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }

    private fun openReceiver(
        title: String,
        year: String,
        description: String,
    ): Intent = Intent().apply {
        action = Intent.ACTION_SEND
        addCategory(Intent.CATEGORY_DEFAULT)
        type = "text/plain"
        putExtra(PLD_TITLE, title)
        putExtra(PLD_YEAR, year)
        putExtra(PLD_DESCR, description)
    }
}