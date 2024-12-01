package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.buttonCloseStack).setOnClickListener {
            finishAffinity()
        }

        findViewById<Button>(R.id.buttonOpenActivityA).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonOpenActivityD).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonCloseActivityC).setOnClickListener {
            finish()
        }
    }
}