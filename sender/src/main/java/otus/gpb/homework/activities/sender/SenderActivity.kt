package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import java.util.Random

class SenderActivity : AppCompatActivity() {

    private var movies: MutableList<Payload> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        moviesInit()

        googleMapsOnAction()
        emailOnAction()
        receiverOnAction()
    }

    private fun moviesInit() {
        movies.add(Payload(getString(R.string.the_nice_guys_title), getString(R.string.the_nice_guys_year), getString(R.string.the_nice_guys_description)))
        movies.add(Payload(getString(R.string.interstellar_title), getString(R.string.interstellar_year), getString(R.string.interstellar_description)))
    }
    private fun googleMapsOnAction() {
        findViewById<AppCompatButton>(R.id.to_google_maps).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("geo:0,0?z=10&q=Moscow&q=restaurants")
                }
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
            catch (exception: ActivityNotFoundException) {
                Log.d("APP_LOG", "No Email Activity")
            }
        }
    }

    private fun emailOnAction() {
        findViewById<AppCompatButton>(R.id.send_email).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    data = Uri.parse("mailto:android@otus.ru")
                    putExtra(Intent.EXTRA_SUBJECT, "Уведомление")
                    putExtra(Intent.EXTRA_TEXT, "Добрый день! Рассылка уведомлений, отвечать не нужно!")
                }
                val intentWithChooserView = Intent.createChooser(intent, "Выбрать почтовый клиент")
                startActivity(intentWithChooserView)
            }
            catch (exception: ActivityNotFoundException) {
                Log.d("APP_LOG", "No Email Activity")
            }
        }
    }

    private fun receiverOnAction() {
        findViewById<AppCompatButton>(R.id.open_receiver).setOnClickListener {
            try {
                val index = Random().nextInt(2)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    addCategory(Intent.CATEGORY_DEFAULT)
                    putExtra(Payload.MOVIE_TITLE, movies[index].title)
                    putExtra(Payload.MOVIE_YEAR, movies[index].year)
                    putExtra(Payload.MOVIE_DESCRIPTION, movies[index].description)
                    type = "text/plain"
                }
                startActivity(intent)
            }
            catch (exception: ActivityNotFoundException) {
                Log.d("APP_LOG", "No Receive Activity")
            }
        }
    }
}