package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SenderActivity : AppCompatActivity() {

    private val toGoogleMaps by lazy{findViewById<Button>(R.id.ToGoogleMaps)}
    private val sendEmail by lazy{findViewById<Button>(R.id.SendEmail)}
    private val openActivity by lazy{findViewById<Button>(R.id.OpenReceiver)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        toGoogleMaps.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Рестораны"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        sendEmail.setOnClickListener{
            //todo
        }

        openActivity.setOnClickListener{
            //todo
        }

    }

}