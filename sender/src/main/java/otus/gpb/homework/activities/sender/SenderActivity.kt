package otus.gpb.homework.activities.sender

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
            //todo
        }

        sendEmail.setOnClickListener{
            //todo
        }

        openActivity.setOnClickListener{
            //todo
        }

    }

}