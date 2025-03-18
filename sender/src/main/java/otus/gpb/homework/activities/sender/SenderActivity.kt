package otus.gpb.homework.activities.sender

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.CancellationTokenSource
import otus.gpb.homework.activities.receiver.R

private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        val buttonToGoogleMaps = findViewById<Button>(R.id.button_to_google_maps)
        buttonToGoogleMaps.setOnClickListener {
            val currentLocation = getCurrentLocation()

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo: ${currentLocation?.longitude}, ${currentLocation?.latitude}?q=Рестораны")
            ).setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        val buttonSendMail = findViewById<Button>(R.id.button_send_email)
        buttonSendMail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "homework Activity #2")
                putExtra(Intent.EXTRA_TEXT, "Hello world!")

            }
            startActivity(intent)
        }

        val buttonOpenReceiver = findViewById<Button>(R.id.button_open_receiver)
        buttonOpenReceiver.setOnClickListener {
            val intent = Intent()
            intent.apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", "Интерстеллар")
                putExtra("year", "2014")
                putExtra(
                    "description",
                    "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
                )
            }

            val result = runCatching { startActivity(intent) }
            result.onFailure { e -> Toast.makeText(this, e.message, Toast.LENGTH_LONG).show() }

        }
    }

    private fun getCurrentLocation(): Location? {
        var currentLocation: Location? = null

        val cancellationTokenSource = CancellationTokenSource()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }

        @Suppress("DEPRECATION")
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            currentLocation = location
        }
        return currentLocation
    }
}