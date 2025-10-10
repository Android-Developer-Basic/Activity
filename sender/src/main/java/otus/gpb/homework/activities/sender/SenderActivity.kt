package otus.gpb.homework.activities.sender

import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
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
        val mapButton: Button = findViewById(R.id.googleMapButton)
        val mailButton: Button = findViewById(R.id.mailButton)
        val recvButton: Button = findViewById(R.id.recvButton)

        mapButton.setOnClickListener{
            val gmmIntentUri = Uri.parse("geo:55.79912949569792, 37.61286149643574?q=restaurants")
            val intentActivityGoMap = Intent( Intent.ACTION_VIEW, gmmIntentUri )
            intentActivityGoMap.setPackage("com.google.android.apps.maps")
            startActivity( intentActivityGoMap )
        }

        mailButton.setOnClickListener{
            val intentActivityMail = Intent( Intent.ACTION_SENDTO ).apply {
                data = Uri.parse("mailto:")
                putExtra( Intent.EXTRA_EMAIL, arrayOf("masenko@pay-lab.ru") )
                putExtra(Intent.EXTRA_SUBJECT, "Test letter")
                putExtra(Intent.EXTRA_TEXT, "Hello Otus!!!")
            }
            startActivity( intentActivityMail )
        }

        recvButton.setOnClickListener {
            val intentActivityRecv = Intent(Intent.ACTION_SEND).apply {
                setType("text/plain")
                addCategory(Intent.CATEGORY_DEFAULT)

                putExtra("title", "Славные парни")
                putExtra("year", "2016")
                putExtra( "description", "Что бывает, когда напарником брутального костолома ...." )
            }
            startActivity( intentActivityRecv )
        }

    }
}