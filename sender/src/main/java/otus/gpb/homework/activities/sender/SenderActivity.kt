package otus.gpb.homework.activities.sender

import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySenderBinding
    val image1 = "interstellar"
    val image2 = "niceguys"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
        binding = ActivitySenderBinding.inflate(layoutInflater)
        binding.openMapsButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=restaurants")
            ).setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        binding.sendEmailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO) // it's not ACTION_SEND
            intent.putExtra(Intent.EXTRA_EMAIL, "android@otus.ru")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email")
            intent.putExtra(Intent.EXTRA_TEXT, "Body of email")
            intent.data = Uri.parse("mailto:") // or just "mailto:" for blank
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent)
        }

        binding.openReceiverButton.setOnClickListener {
            val movie = Payload(
                title = "Top Gun",
                year = "2022",
                description = "Top Gun is a 1986 American action drama film[2] directed by Tony Scott and produced by Don Simpson and Jerry Bruckheimer, with distribution by Paramount Pictures. "
            )

            startActivity(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    addCategory(Intent.CATEGORY_DEFAULT)
                    putExtra("title", movie.title)
                    putExtra("year", movie.year)
                    putExtra("description", movie.description)
                    putExtra("image", image2)
                }
            )
        }


        setContentView(binding.root)
    }
}