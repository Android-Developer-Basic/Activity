package otus.gpb.homework.activities.sender

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat




class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.Maps).setOnClickListener(){
            val locationManager: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val providers: List<String> = locationManager.getProviders(true)
            var location: Location? = null

            var permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
            permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            }

            for (i in providers.size - 1 downTo 0) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                location= locationManager.getLastKnownLocation(providers[i])
                if (location != null)
                    break
            }
            if (location != null) {
                val geoUriString = "geo:0,0?q=ресторан+рядом&z=2"
                val geoUri: Uri = Uri.parse(geoUriString)

                intent = Intent(this, MapsActivity::class.java)
                //intent = Intent("MapsActivity", geoUri)
                intent.putExtra("lat", location.latitude)
                intent.putExtra("lon", location.longitude)
                intent.setData(geoUri)
                //startActivity(intent)

                val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
                else
                {
                    Toast.makeText(this, "Warning!!", Toast.LENGTH_SHORT).show()
                }

        }
        findViewById<Button>(R.id.EMail).setOnClickListener(){
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.setType("text/plain")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Message Body")
            startActivity(emailIntent)
        }
        findViewById<Button>(R.id.Receiver).setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.addCategory("category.DEFAULT")

            intent.putExtra("title", "Интерстеллар")
            intent.putExtra("imageName", "R.id.interstellar")
            intent.putExtra("year", "2014")
            intent.putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
            //intent.setClassName("otus.gpb.homework.activities.receiver", "ReceiverActivity")
            intent.component = ComponentName("otus.gpb.homework.activities.receiver", "otus.gpb.homework.activities.receiver.ReceiverActivity")
            startActivity(intent)
        }
    }
}