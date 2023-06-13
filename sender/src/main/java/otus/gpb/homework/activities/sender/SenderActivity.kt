package otus.gpb.homework.activities.sender

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SenderActivity : AppCompatActivity() {
    private lateinit var locationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        locationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onStart() {
        super.onStart()
        val buttonMaps = findViewById<Button>(R.id.buttonMaps)
        buttonMaps.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                locationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val gmmIntentUri =
                            Uri.parse("geo:${location.latitude},${location.longitude}?q=рестораны")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                    }
                }
            }
        }
        val buttonEmail = findViewById<Button>(R.id.buttonEmail)
        buttonEmail.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "Тестовое письмо")
                putExtra(Intent.EXTRA_TEXT, "Привет! Это тестовое письмо.")
            }
            startActivity(emailIntent)
        }
        val buttonReceiver = findViewById<Button>(R.id.buttonReceiver)
        buttonReceiver.setOnClickListener{

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra("title", "Интерстеллар")
                putExtra("year", "2014")
                putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
            }
            startActivity(sendIntent)
        }

    }
}