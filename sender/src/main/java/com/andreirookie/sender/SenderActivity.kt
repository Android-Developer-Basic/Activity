package com.andreirookie.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val toGoogleMapsButton = findViewById<Button>(R.id.toGoogleMaps)
        val toSendEmailButton = findViewById<Button>(R.id.toSendEmail)
        val toOpenReceiverButton = findViewById<Button>(R.id.toOpenReceiver)

        toGoogleMapsButton.setOnClickListener {
            try {
                val uri = Uri.parse("geo:0.0?q=restaurants")
                val mapsIntent = Intent(Intent.ACTION_VIEW, uri)
                mapsIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapsIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "No GoogleMaps on yourdevice", Toast.LENGTH_SHORT).show()
            }
        }

        toSendEmailButton.setOnClickListener {
            try {
                val emailIntent = Intent().apply {
                    action = Intent.ACTION_SENDTO
                    data = Uri.parse("mailto:android@otus.ru")
                    putExtra(Intent.EXTRA_TEXT, "Hello, OTS")
                    putExtra(Intent.EXTRA_SUBJECT, "Greetings")
                }
            val emailIntentWithChooser = Intent.createChooser(emailIntent, "Send to..")
                startActivity(emailIntentWithChooser)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        toOpenReceiverButton.setOnClickListener {
                try {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        addCategory(Intent.CATEGORY_DEFAULT)
                        putExtra("title", "Славные парни")
                        putExtra("year", "2016")
                        putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
                        type= "text/plain"
                    }
                    startActivity(Intent.createChooser(intent, "Choose wisely..."))
//                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "No such app", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
