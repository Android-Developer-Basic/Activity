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
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        findViewById<Button>(R.id.Maps).setOnClickListener(){
            val geoUriString = "geo:0,0?q=ресторан+рядом&z=2"
            val geoUri: Uri = Uri.parse(geoUriString)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)


        }
        findViewById<Button>(R.id.EMail).setOnClickListener(){
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Message Body")
            startActivity(emailIntent)
        }
        findViewById<Button>(R.id.Receiver).setOnClickListener(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.addCategory("android.intent.category.DEFAULT")
            intent.putExtra("title", "Интерстеллар")
            intent.putExtra("imageName", "R.id.interstellar")
            intent.putExtra("year", "2014")
            intent.putExtra("description", getString(R.string.interstellar_large))

            startActivity(intent)


        }
    }
}