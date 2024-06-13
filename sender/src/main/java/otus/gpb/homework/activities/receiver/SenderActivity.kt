package otus.gpb.homework.activities.receiver

import android.app.IntentService
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySenderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        }

    fun onClickBtGoogleMap(view: View){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:100,200?q=Рестораны"))
            .setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    fun onClickMail(view: View){
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:android@otus.ru",))
            .putExtra(Intent.EXTRA_SUBJECT,"Ну это тема письма")
            .putExtra(Intent.EXTRA_TEXT, "А это текст письма")
        startActivity(intent)
    }

    fun btnOpenReceiver(view: View){
        val intent: Intent = openReceiver(
            "Интерстеллар",
            "2014",
            "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями."
        )
        startActivity(intent)
    }


    private fun openReceiver(
        title: String,
        year: String,
        description: String,
    ): Intent = Intent().apply {
        action = Intent.ACTION_SEND
        addCategory(Intent.CATEGORY_DEFAULT)
        type = "text/plain"
        putExtra(PLD_TITLE, title)
        putExtra(PLD_YEAR, year)
        putExtra(PLD_DESCR, description)
    }

}