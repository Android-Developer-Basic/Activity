package otus.gpb.homework.activities.sender

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

        btnToGoogleMaps.setOnClickListener {  }
        btnSendEmail.setOnClickListener {  }
        btnOpenReceiver.setOnClickListener {  }
    }
}