package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.toGoogleMapsButton.setOnClickListener {
            openGoogleMaps()
        }

        binding.sendEmailButton.setOnClickListener {
            sendEmail()
        }

        binding.openReceiverButton.setOnClickListener {
            openReceiver()
        }

        binding.toGoogleMapsButton.isEnabled = true
        binding.sendEmailButton.isEnabled = true
        binding.openReceiverButton.isEnabled = true
    }

    private fun openGoogleMaps() {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=restaurants")
                setPackage("com.google.android.apps.maps")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/restaurants"))
                startActivity(webIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendEmail() {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:android@otus.ru")
                putExtra(Intent.EXTRA_SUBJECT, "Homework Activity #2")
                putExtra(Intent.EXTRA_TEXT, "Hello from OTUS Android Developer course!")
            }

            val chooser = Intent.createChooser(intent, "Send Email")
            startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openReceiver() {
        try {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)

                putExtra("title", "Interstellar")
                putExtra("year", "2014")
                putExtra("description", "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.")
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                android.widget.Toast.makeText(this, "No app found to handle this intent", android.widget.Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            android.widget.Toast.makeText(this, "Error: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}