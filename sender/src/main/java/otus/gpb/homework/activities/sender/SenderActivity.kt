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
        val bundle = getBundle(Payload())

        findViewById<Button>(R.id.toGoogleMaps).setOnClickListener { toGoogleMaps() }
        findViewById<Button>(R.id.sendEmail).setOnClickListener { sendEmail() }
        findViewById<Button>(R.id.openReceiver).setOnClickListener { openReceiver(bundle) }
    }

    private fun getBundle(data: Payload): Bundle {
        val bundle = Bundle()
        bundle.putString("title", data.title)
        bundle.putString("year", data.year)
        bundle.putString("description", data.description)
        return bundle
    }

    private fun toGoogleMaps() {

        val intentGoogleMaps = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:0,0?q=restaurants")
        ).setPackage("com.google.android.apps.maps")
        startActivity(intentGoogleMaps)
    }

    private fun sendEmail() {

        val intentMail = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("mailto:android@otus.ru")
        ).putExtra(Intent.EXTRA_SUBJECT, "homework")
            .putExtra(Intent.EXTRA_TEXT, "Send feedback")
        startActivity(intentMail)
    }

    private fun openReceiver(bundle: Bundle) {

        val intentReceiver = Intent(Intent.ACTION_SEND).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            setType("text/plain")
            putExtras(bundle)
        }
        startActivity(intentReceiver)
    }
}