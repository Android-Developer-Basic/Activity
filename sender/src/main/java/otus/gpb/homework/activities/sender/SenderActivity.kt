package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlin.random.Random

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        findViewById<Button>(R.id.to_map)?.setOnClickListener {
            val uri = Uri.parse("geo:0.0?q=рестораны")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Не найдены карты Гугл", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.send_email)?.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            with(intent) {
                putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
                putExtra(Intent.EXTRA_SUBJECT, "письмо счастья")
                putExtra(Intent.EXTRA_TEXT, "счастье есть")
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Не найдены почтовые клиенты", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.open_receiver)?.setOnClickListener {

            val film = getFilms(getString(R.string.films)).let {
                it[Random.nextInt(0, it.size)]
            }

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", film.title)
                putExtra("year", film.year)
                putExtra("description", film.description)
            }
            startActivity(intent)
        }
    }

    private fun getFilms(str: String) = mutableListOf<Payload>().apply {
        str.split("\n").filter { it.isNotBlank() }.chunked(3)
            .forEach {
                add(
                    Payload(
                        it[0].substringAfter(":").trim(),
                        it[1].substringAfter(":").trim(),
                        it[2].substringAfter(":").trim()
                    )
                )
            }
    }
}