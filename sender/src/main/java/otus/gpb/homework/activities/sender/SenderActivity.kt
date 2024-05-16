package otus.gpb.homework.activities.sender

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button?>(R.id.openMap)
            .setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:38.5411,-0.1225?q=restaurant")
                    ).setPackage("com.google.android.apps.maps")
                )
            }

        findViewById<Button?>(R.id.sendEmail)
            .setOnClickListener {
                startActivity(Intent().setAction(Intent.ACTION_SENDTO)
                    .apply {
                        data = Uri.parse("mailto:android@otus.ru")
                        putExtra(Intent.EXTRA_SUBJECT, "Some kind of")
                        putExtra(Intent.EXTRA_TEXT, "Some kind of")
                    }
                )
            }

        findViewById<Button?>(R.id.openReceiver)
            .setOnClickListener {
                val movie = Payload(
                    "The Nice Guys",
                    "2016",
                    "What happens when a slender mug becomes a partner of a brutal bonebreaker? Hired security guard Jackson Healy and private detective Holland March are forced to work together to unravel a thorny missing girl case that turns into the crime of the century.\n" +
                            "\n" +
                            "Will the guys be able to solve a complex puzzle if each of them has their own, very individual methods?"
                )
                startActivity(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        addCategory(Intent.CATEGORY_DEFAULT)
                        putExtra("title", movie.title)
                        putExtra("year", movie.year)
                        putExtra("description", movie.description)
                    }
                )
            }
    }
}
