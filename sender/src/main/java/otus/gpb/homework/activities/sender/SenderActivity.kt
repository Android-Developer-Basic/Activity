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
        val googleMaps = findViewById<Button>(R.id.maps_button)
        googleMaps.setOnClickListener {
            try {
                val navigationIntentUri =
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=restaurant")
                val mapIntent = Intent(Intent.ACTION_VIEW, navigationIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            } catch (exception: ActivityNotFoundException){
                Toast.makeText(this, getString(R.string.no_app_toast), Toast.LENGTH_LONG).show()
            }
        }
        val sendEmail = findViewById<Button>(R.id.email_button)
        sendEmail.setOnClickListener {
            try {
                val mailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.otus_email)))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.mail_text))
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(mailIntent)
                }
            } catch (exception: ActivityNotFoundException){
                Toast.makeText(this, getString(R.string.mail_client_exception_notification), Toast.LENGTH_LONG).show()
            }
        }
        val openReceiver = findViewById<Button>(R.id.receiver_button)
        openReceiver.setOnClickListener {
            val receiverIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", getString(R.string.niceguys_title))
                putExtra("year", "2016")
                putExtra("description", getString(R.string.nice_guys_description))
            }
            startActivity(receiverIntent)

        }
    }
}