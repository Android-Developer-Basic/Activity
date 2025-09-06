package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.os.Message
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AbstractAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.button_open_activity_a).setOnClickListener {
            val intentActivityA = Intent(this, ActivityA::class.java)
            intentActivityA.addFlags(FLAG_ACTIVITY_SINGLE_TOP or FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentActivityA)
        }
        findViewById<Button>(R.id.button_open_activity_d).setOnClickListener {
            val intentActivityD = Intent(this, ActivityD::class.java)
            finishAffinity()
            startActivity(intentActivityD)
        }
        findViewById<Button>(R.id.button_close_activity_c).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.button_close_stack).setOnClickListener {
            ActivityB.finishAndRemoveTaskHCallback.handleMessage(Message())
        }
    }
}
