package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R
import ru.tilman.payload.PAYLOAD_KEY
import ru.tilman.payload.Payload
import kotlin.random.Random

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.to_google_maps_button).setOnClickListener {
            val intent = createMapsIntent()
            runCatching { startActivity(intent) }
                .getOrElse {
                    handleOpenClientError(it, getString(R.string.no_maps_clients_installed))
                }

        }
        findViewById<Button>(R.id.send_email_button).setOnClickListener {
            val intent = createEmailIntent()
            runCatching { startActivity(intent) }
                .getOrElse {
                    handleOpenClientError(it, getString(R.string.no_email_clients_installed))
                }
        }
        findViewById<Button>(R.id.open_receiver_button).setOnClickListener {
            val intent = createSendPayloadIntent(payloadProvider.next())
            runCatching { startActivity(intent) }
                .getOrElse {
                    handleOpenClientError(it, getString(R.string.no_payload_clients_installed))
                }
        }

    }

    /**
     * Формирует [Intent] для отправки [Payload]
     * Тут отступил от задания, так как было интересно отправить более сложный объект, чем строку
     * Попутно разобрался с созданием Android библиотеки
     * для переиспользования класса данных и ключв передаваемого объекта
     * */
    private fun createSendPayloadIntent(payload: Payload): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            setType("text/plain")
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra(PAYLOAD_KEY, payload)
        }
    }


    private fun createMapsIntent(): Intent {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:?q=Рестораны&z=18")
            setPackage("com.google.android.apps.maps")
        }
        return intent
    }

    private fun handleOpenClientError(it: Throwable, viewClientError: String) {
        Log.e("debug_activity", it.message, it)
        if (it is ActivityNotFoundException)
            Toast.makeText(this, viewClientError, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Unsupported error.", Toast.LENGTH_SHORT).show()
    }

    private fun createEmailIntent(): Intent {
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            putExtra(Intent.EXTRA_SUBJECT, "Тестовое письмо")
            putExtra(
                Intent.EXTRA_TEXT, """
                        Добрый день!
    
                        Это тестовое письмо.
                    """.trimIndent()
            )
        }
    }

    /**
     * Добавлен рандомайзе полезной нагрузки для отдладки разных вариантов,
     * в том числе когда у получателя не найдены данные по нагрузке
     * */
    private val payloadProvider = object {
        private val payloadArray: Array<Payload> = arrayOf(
            Payload(
                "Славные парни",
                "2016",
                "Что бывает, когда напарником брутального костолома становится субтильный лопух? Наемный охранник Джексон Хили и частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать плевое дело о пропавшей девушке, которое оборачивается преступлением века. Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма индивидуальные методы."
            ),
            Payload(
                "Интерстеллар",
                "2014",
                "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
            ),

            Payload(
                "Джентельмены удачи",
                "1971",
                "Заведующему детсадом Трошкину фатально не повезло: он оказался как две капли воды похож на бандита по кличке «Доцент», похитившего уникальный шлем Александра Македонского. Милиция внедряет добряка Трошкина в воровскую среду - и ему ничего не остается, кроме как старательно изображать своего двойника-злодея, путая всех окружающих. Со временем он настолько блестяще входит в роль, что сам начинает порой приходить в ужас. Между тем, жизни его угрожает смертельная опасность...."
            )

        )


        fun next() = payloadArray[Random.nextInt(0, payloadArray.size)]
    }
}

