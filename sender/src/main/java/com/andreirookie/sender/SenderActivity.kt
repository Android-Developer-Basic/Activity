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
//            С гулкартами интент явный, но что, если они не установлены, в трай-кетч?Да, на данный момент актуален try catch, ранее была возможность проверки через
//            intent.resolveActivity(packageManager), но с sdk 30 данное действие просит разрешение
                val uri = Uri.parse("geo:0.0?q=restaurants")
                val mapsIntent = Intent(Intent.ACTION_VIEW, uri)
                mapsIntent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(mapsIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "No GoogleMaps on yourdevice", Toast.LENGTH_SHORT).show()
            }
        }

        toSendEmailButton.setOnClickListener {
            val email = "android@otus.ru"
            val body = "Hello, OTS"
            val subject = "Greetings"

                val emailIntent = Intent().apply {
                    action = Intent.ACTION_SENDTO
                    data = Uri.parse("mailto:$email?subject=$subject&body=$body")

//                    following doesn't work with gmail, though with yandexmail works well
//                    putExtra(Intent.EXTRA_TEXT, "Hello, OTS")
//                    putExtra(Intent.EXTRA_SUBJECT, "Greetings")
                }
            val emailIntentWithChooser = Intent.createChooser(emailIntent, "Send to..")
            try {
                startActivity(emailIntentWithChooser)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        toOpenReceiverButton.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        addCategory(Intent.CATEGORY_DEFAULT)
                        putExtra("title", "Славные парни")
                        putExtra("year", "2016")
                        putExtra("description", "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы.")
                        type= "text/plain"
                    }
                try {
//              в случае с toOpenReceiver не выводится заголовок в чузере, пишет просто share, в чем причина может быть?
//              В документации метода указано, что это опциональное свойство , которое не будет показываться для данного действия
//              title – Optional title that will be displayed in the chooser,
//              only when the target action is not ACTION_SEND or ACTION_SEND_MULTIPLE.
                    startActivity(Intent.createChooser(intent, "Choose wisely..."))
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "No such app", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
