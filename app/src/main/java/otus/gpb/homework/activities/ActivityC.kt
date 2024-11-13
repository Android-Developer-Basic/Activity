package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        val buttonOpenActA = findViewById<Button>(R.id.buttonOpenActivityA)
        buttonOpenActA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        val buttonOpenActD = findViewById<Button>(R.id.buttonOpenActivityD)
        buttonOpenActD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            finishAffinity()
            startActivity(intent)
        }

        val buttonCloseActC = findViewById<Button>(R.id.buttonCloseActivityС)
        buttonCloseActC.setOnClickListener {
            finish()
        }

        val buttonCloseStack = findViewById<Button>(R.id.buttonCloseStack)
        buttonCloseStack.setOnClickListener {
            finishAffinity()
        }
    }
}