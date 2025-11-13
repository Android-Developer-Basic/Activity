package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import de.cketti.mailto.EmailIntentBuilder
import otus.gpb.homework.activities.receiver.R

class SenderActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnGoogleMaps: Button
    private lateinit var btnEmail: Button
    private lateinit var btnReceiver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sender)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnGoogleMaps = findViewById(R.id.btn_google_maps)
        btnGoogleMaps.setOnClickListener(this)

        btnEmail = findViewById(R.id.btn_email)
        btnEmail.setOnClickListener(this)

        btnReceiver = findViewById(R.id.btn_send)
        btnReceiver.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        try {
            when (v.id) {
                R.id.btn_google_maps -> {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=restaurants"))
                            .setPackage("com.google.android.apps.maps")
                    )
                }

                R.id.btn_email -> {

                    var status = EmailIntentBuilder.from(this)
                        .to("android@otus.ru")
                        .subject("ДЗ Activity_02")
                        .body("Тело сообщения")
                        .start()
                }

                R.id.btn_send -> {
                    // todo: Переделать ради интереса на Parcelable. Добавить новый модуль common с Payload + методами десереализации
                    val intent: Intent = Intent("otus.intent.payload")
                    intent.type = "text/plain"
                    intent.addCategory(Intent.CATEGORY_DEFAULT)

                    val payload = Payload(
                        "Интерстеллар",
                        "2014",
                        " Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
                    )

                    val bundle = Bundle()

                    bundle.putString("title", payload.title)
                    bundle.putString("year", payload.year)
                    bundle.putString("description", payload.description)

                    intent.putExtra("payload", bundle)

                    startActivity(intent)
                }
            }
        } catch (ex: Exception) {
            Log.d("Компонент " + v.resources.getResourceEntryName(v.id), ex.message.toString())
        }

    }

}