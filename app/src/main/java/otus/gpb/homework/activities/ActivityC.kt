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

        findViewById<Button>(R.id.button_open_a)?.setOnClickListener {
            val intent = Intent().setClass(this, ActivityA::class.java)

            startActivity(intent)
        }

        findViewById<Button>(R.id.button_open_d)?.setOnClickListener {
            val intent = Intent().setClass(this, ActivityD::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            startActivity(intent)
        }

        findViewById<Button>(R.id.button_close_c)?.setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.button_close_stack)?.setOnClickListener {
            finishAffinity()
        }
    }
}