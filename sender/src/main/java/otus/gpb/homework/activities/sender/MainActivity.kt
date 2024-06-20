package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val movies: MutableList<Payload> = mutableListOf()

    private fun moviesInit() {
        movies.add(Payload(getString(R.string.the_nice_guys_title), getString(R.string.the_nice_guys_year), getString(R.string.the_nice_guys_description)))
        movies.add(Payload(getString(R.string.interstellar_title), getString(R.string.interstellar_year), getString(R.string.interstellar_description)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesInit()
    }

    override fun onStart() {
        super.onStart()
        val buttonToGoogleMaps = findViewById<Button>(R.id.buttonToGoogleMaps)
        val buttonSendEmail = findViewById<Button>(R.id.buttonSendEmail)
        val buttonOpenReceiver = findViewById<Button>(R.id.buttonOpenReceiver)
        /** По клику на кнопку “To Google Maps”, используя явный Intent вызовите Activity
         *  приложения Google Maps.
         *  После того как Google Maps поймает ваш Intent,
         *  в нем должны отобразиться ближайшие к текущей геолокации места по тэгу “Рестораны” */
        buttonToGoogleMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
           try {
                startActivity(mapIntent)
            } catch (exception: ActivityNotFoundException){
                Toast.makeText(this, "Not found Gmaps", Toast.LENGTH_SHORT).show()
            }
            /*mapIntent.resolveActivity(packageManager)?.let { // Не сработал этот код, непонял почему(
                 startActivity(mapIntent)
             }*/
        }
        /** По клику на кнопку “Send Email” отправьте неявный Intent в метод startActivity()
         * Этот Intent должны уметь обработать любые почтовые клиенты(если они реализовали
         * intent-filter согласно контракту).
         * В качестве адресата используйте ящик android@otus.ru,
         * тему и содержание письма придумайте сами. */
        buttonSendEmail.setOnClickListener {
            val intentSentEmail = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:android@otus.ru?subject=First mail send&body=Hi,I found this on website https://stackoverflow.com/questions/4782068/can-i-set-subject-content-of-email-using-mailto")
            }
            try {
                startActivity(intentSentEmail)
            } catch (exception: ActivityNotFoundException){
                Toast.makeText(this, "Not found email Apps", Toast.LENGTH_SHORT).show()
            }
        }
        /**  */
        buttonOpenReceiver.setOnClickListener {
            val intentOpenReceiver = Intent(Intent.ACTION_SEND).apply {
                addCategory(Intent.CATEGORY_DEFAULT) // можно в ревью описать что за категории или ссылку дать где об этом почитать, зачем это неадо?
                type = "text/plain"
                putExtra("MOVIE_NAME", movies[0].title)
                putExtra("MOVIE_YEAR", movies[0].year)
                putExtra("MOVIE_DESCRIPTION", movies[0].description)
            }
            try {
                startActivity(intentOpenReceiver)
            } catch (exception: ActivityNotFoundException){
                Toast.makeText(this, "Not found Receiver Apps", Toast.LENGTH_SHORT).show()
            }
        }
    }
}