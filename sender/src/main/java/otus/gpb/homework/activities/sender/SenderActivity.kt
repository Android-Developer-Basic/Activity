package otus.gpb.homework.activities.sender

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

//import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {
    // Координаты по умолчанию (центр Москвы), если GPS не отдаст ничего
    private var geoLatitude = 55.7558
    private var geoLongitude = 37.6176
    private val intentGM = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$geoLatitude,$geoLongitude?q=Рестораны"))
    private val geoPermisson = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted ->
        if (isGranted) {
            Toast.makeText(
                this,
                "Получено разрешение на геолокацию",
                Toast.LENGTH_LONG
            ).show()
            // TODO geo из телефона
            startActivity(intentGM)
        } else {
            Toast.makeText(
                this,
                "Для работы карт нужно разрешение на геолокацию",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        // Находим кнопки
        val buttonGM = findViewById<Button>(R.id.buttonGM)
        val buttonSE = findViewById<Button>(R.id.buttonSE)
        val buttonOR = findViewById<Button>(R.id.buttonOR)

        // --- Обработчик для Google Maps ---
        buttonGM.setOnClickListener {
            // проверим права
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            openMapsWithLastLocation()
                Toast.makeText(
                    this,
                    "Права на geo есть",
                    Toast.LENGTH_LONG
                ).show()
               // TODO geo из телефона
               startActivity(intentGM)
            } else {
               Toast.makeText(
                   this,
                   "Необходимы разрешения на геолокацию",
                   Toast.LENGTH_LONG
               ).show()
                geoPermisson.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        // --- Обработчик для Send Email (РЕАЛИЗАЦИЯ) ---
        buttonSE.setOnClickListener {
            val email = "android@otus.ru"
//            val ccEmail = "fortoke@jenv.ru"
            val subject = "Test homework"
            val body = "This homework complete"

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"  // MIME-тип для email, чтобы отфильтровать только почтовые клиенты

            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
//            intent.putExtra(Intent.EXTRA_CC, arrayOf(ccEmail))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)

            // Проверка: есть ли приложение, которое может обработать этот Intent
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // Если почтовых клиентов нет надо бы сообщить
                Toast.makeText(
                    this,
                    "На устройстве не найдено приложение для отправки почты",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        // --- Обработчик для Open Receiver ---

        buttonOR.setOnClickListener {
            val movies = listOf(
                mapOf(
                    "title" to "Славные парни",
                    "year" to "2016",
                    "description" to "Что бывает, когда напарником брутального костолома становится субтильный лопух? " +
                            "Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, " +
                            "чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века."
                ),
                mapOf(
                    "title" to "Интерстеллар",
                    "year" to "2014",
                    "description" to "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, " +
                            "коллектив исследователей и учёных отправляется сквозь червоточину, чтобы найти планету " +
                            "с подходящими для человечества условиями."
                )
            )

            // Выбираем случайный фильм
            var selectedMovie = movies.random()
            Toast.makeText(this, "{$selectedMovie[\"title\"]}", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                addCategory(Intent.CATEGORY_DEFAULT)
                putExtra("title", selectedMovie["title"])
                putExtra("year", selectedMovie["year"])
                putExtra("description", selectedMovie["description"])
            }

            startActivity(intent)

        }
    }

}