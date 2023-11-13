package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonToGoogleMaps = findViewById<Button>(R.id.buttonToGoogleMaps)
        val buttonSendEmail = findViewById<Button>(R.id.buttonSendEmail)
        val buttonOpenReceiver = findViewById<Button>(R.id.buttonOpenReceiver)

        buttonToGoogleMaps.setOnClickListener {
            // обработчик нажатия для кнопки "To Google Maps"
        }

        buttonSendEmail.setOnClickListener {
            // обработчик нажатия для кнопки "Send Email"
        }

        buttonOpenReceiver.setOnClickListener {
            // обработчик нажатия для кнопки "Open Receiver"
        }
    }
}