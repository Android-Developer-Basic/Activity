package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity(R.layout.activity_sender) {

    private val btnToGoogleMaps by lazy {
        findViewById<Button>(R.id.btnToGoogleMaps)
    }

    private val btnSendEmail by lazy {
        findViewById<Button>(R.id.btnSendEmail)
    }

    private val btnOpenReceiver by lazy {
        findViewById<Button>(R.id.btnOpenReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnToGoogleMaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:0,0?q=restaurants")
            startActivity(intent)
        }

        btnSendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
                .apply {
                    data = Uri.parse("mailto:${getString(R.string.emailOtus)}?subject=${getString(R.string.emailSubject)}&body=${getString(R.string.emailText)}")
                    /*
                    TODO если использовать в Активти putExtra. То Test проверку не проходит.
                     Решил оставить на потом разобраться по чему не работет.
                    putExtra(Intent.EXTRA_EMAIL, getString(R.string.emailOtus))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSubject))
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.emailText))
                    */
                }
            startActivity(intent)
        }
        btnOpenReceiver.setOnClickListener { }
    }
}