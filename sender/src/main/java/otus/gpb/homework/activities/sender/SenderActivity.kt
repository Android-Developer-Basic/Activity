package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.security.SecureRandom

const val TITLE_TAG = "title"
const val DESCRIPTION_TAG = "desc"
const val YEAR_TAG = "year"

 class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.bt_to_google_maps).setOnClickListener {
            val uri: Uri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.bt_send_email).setOnClickListener {
            val uri = Uri.parse(
                "mailto:${getString(R.string.otus_address)}?subject=${getString(R.string.default_mail_title)}&body=${
                    getString(R.string.default_mail_message)
                }"
            )
            val mailIntent = Intent(Intent.ACTION_SENDTO, uri)

            try {
                startActivity(Intent.createChooser(mailIntent, "Send mail"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.bt_open_receiver).setOnClickListener {
            val payload = getRandomPayload()
            val filmIntent = Intent("Action.SEND").apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/plain"
                putExtra(TITLE_TAG, payload.title)
                putExtra(DESCRIPTION_TAG, payload.description)
                putExtra(YEAR_TAG, payload.year)

            }
            try {
                startActivity(filmIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, getString(R.string.app_not_found), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getRandomPayload(): Payload {
        val payloads: MutableList<Payload> = mutableListOf()
        try {
            val data = InputStreamReader(assets.open("payload.txt"))
            val targetLines: MutableList<String> = mutableListOf()
            val lines = data.readLines()
            lines.forEach {
                targetLines.add(it)
                if (it.isEmpty()) {
                    payloads.add(
                        Payload(
                            targetLines[0].substringAfter(":"),
                            targetLines[1].substringAfter(":"),
                            targetLines[2].substringAfter(":")
                        )
                    )
                    targetLines.clear()
                }
            }
            payloads.add(
                Payload(
                    targetLines[0].substringAfter(":"),
                    targetLines[1].substringAfter(":"),
                    targetLines[2].substringAfter(":")
                )
            )

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
        return  payloads[SecureRandom().nextInt(payloads.size)]
    }
}
