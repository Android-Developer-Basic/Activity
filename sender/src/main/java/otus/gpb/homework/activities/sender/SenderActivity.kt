package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val toGoogleMapsButton: Button = findViewById(R.id.btn_to_google_maps)
        val sendEmailButton: Button = findViewById(R.id.btn_send_email)
        val openReceiverButton: Button = findViewById(R.id.btn_open_receiver)

        toGoogleMapsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Рестораны"))
                .setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        sendEmailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:android@otus.ru",))
                .putExtra(Intent.EXTRA_SUBJECT,"Тема письма")
                .putExtra(Intent.EXTRA_TEXT, "Текст письма")
            startActivity(intent)
        }

        openReceiverButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
        }
    }
}