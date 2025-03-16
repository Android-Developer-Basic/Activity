package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityA : AppCompatActivity() {
    lateinit var openActivityB_BTN : Button
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_a)
        textView = findViewById(R.id.textView)
        textView.text = ActivityUtils.getTaskAffinity(this)

        openActivityB_BTN = findViewById(R.id.openB_BTN)

        openActivityB_BTN.setOnClickListener{
            val intent = Intent(this, ActivityB::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }



        }
}