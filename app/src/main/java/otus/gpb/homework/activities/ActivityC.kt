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
        val buttonOpenActivityA = findViewById<Button>(R.id.buttonOpenActivityA)
        val buttonOpenActivityD = findViewById<Button>(R.id.buttonOpenActivityD)
        val buttonCloseActivityC = findViewById<Button>(R.id.buttonCloseActivityC)
        val buttonCloseStack = findViewById<Button>(R.id.CloseStack)

        buttonOpenActivityA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }
        buttonOpenActivityD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            finishAffinity()
            this.startActivity(intent);
        }

        buttonCloseActivityC.setOnClickListener {
            finish()
        }
        buttonCloseStack.setOnClickListener {
            finishAffinity()
        }
    }
}

