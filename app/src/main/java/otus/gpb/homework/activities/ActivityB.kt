package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityB : AppCompatActivity() {

    lateinit var openC_BTN : Button
    lateinit var textTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b)
        textTV = findViewById(R.id.textTV)
        textTV.text = ActivityUtils.getTaskAffinity(this)

        openC_BTN = findViewById(R.id.openC_BTN)
        openC_BTN.setOnClickListener{
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)
        }
    }
}