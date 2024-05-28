package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R
import otus.gpb.homework.payload.Payload

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val toGoogleMapsButton: Button = findViewById(R.id.to_google_maps)
        val sendMailButton: Button = findViewById(R.id.send_email)
        val openReceiverButton: Button = findViewById(R.id.open_receiver)

        toGoogleMapsButton.setOnClickListener {
            startActivity(toGoogleMaps("restaurants"))
        }
        sendMailButton.setOnClickListener {
            startActivity(sendEmailTo())
        }
        openReceiverButton.setOnClickListener {
            startActivity(openReceiver(Payload.build("Интерстеллар")))
        }
    }


    private fun toGoogleMaps(searchString: String): Intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse("geo:0,0?q=$searchString")
        setPackage("com.google.android.apps.maps")
    }

    private fun sendEmailTo(
        email: String = "android@otus.ru",
        subject: String = "Hello! this is email from homework",
        text: String = "Sorry! We are already looking for the culprit",
    ): Intent = Intent().apply {
        action = Intent.ACTION_SENDTO
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }

    private fun openReceiver(
        payload: Payload,
    ): Intent = Intent().apply {
        action = Intent.ACTION_SEND
        addCategory(Intent.CATEGORY_DEFAULT)
        type = "text/plain"
        putExtra(Payload.TITLE, payload.title)
        putExtra(Payload.YEAR, payload.year)
        putExtra(Payload.DESC, payload.description)
    }

}