package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {

    lateinit var openA_BTN : Button
    lateinit var openD_BTN : Button
    lateinit var closeC_BTN : Button
    lateinit var closeStackBTN : Button
    lateinit var textTV: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        textTV = findViewById(R.id.textTV)
        textTV.text = ActivityUtils.getTaskAffinity(this)

        openA_BTN = findViewById(R.id.openA_BTN)
        openD_BTN = findViewById(R.id.openD_BTN)
        closeC_BTN = findViewById(R.id.closeC_BTN)
        closeStackBTN = findViewById(R.id.closeStackBTN)


        openA_BTN.setOnClickListener{
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        openD_BTN.setOnClickListener{
            val intent = Intent(this, ActivityD::class.java)
            startActivity(intent)
        }

        closeC_BTN.setOnClickListener{
            finish()
        }
        closeStackBTN.setOnClickListener{
            finishAffinity()
        }

        }

}