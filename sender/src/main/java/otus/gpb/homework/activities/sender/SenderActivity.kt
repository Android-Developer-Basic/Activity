package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.R

const val TITLE_KEY = "title key"
const val YEAR_KEY = "year key"
const val DESCR_KEY = "description key"


class SenderActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sender_activity)

        val goToGoogleBtn = findViewById<Button>(R.id.go_to_google_btn)
        val sendEmailBtn = findViewById<Button>(R.id.send_email_btn)
        val openReceiverBtn = findViewById<Button>(R.id.open_receiver_btn)

        goToGoogleBtn.setOnClickListener {
            startActivity(
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = getGoogleMapUri("restaurants")
                    setPackage("com.google.android.apps.maps")
                }
                )
        }

        sendEmailBtn.setOnClickListener {
            startActivity(
                Intent().apply {
                    action = Intent.ACTION_SENDTO
                    data = getEmailUri("android@otus.ru","Good day","Good day")
                }
            )
        }

        openReceiverBtn.setOnClickListener {

            startActivity(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    addCategory(Intent.CATEGORY_DEFAULT)
                    putExtra(TITLE_KEY,"Славные парни")
                    putExtra(YEAR_KEY, "2016")
                    putExtra(DESCR_KEY, "Что бывает, когда напарником брутального костолома" +
                            "становится субтильный лопух? Наемный охранник Джексон Хили и частный" +
                            "детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое" +
                            " дело о пропавшей девушке, которое оборачивается преступлением века."
                            + "Смогут ли парни разгадать сложный ребус, если у каждого из них – свои," +
                            "весьма индивидуальные методы.")
                    type = "text/plain"
                }
            )
        }
    }

    private fun getGoogleMapUri(query: String): Uri = Uri.parse("geo:0,0?q=$query")

    private fun getEmailUri(email: String, subject: String, body: String): Uri =
        Uri.parse("mailto:$email?subject=$subject&body=$body")
}
