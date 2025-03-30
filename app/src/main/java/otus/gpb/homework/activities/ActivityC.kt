package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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

        val buttonThree = findViewById<Button>(R.id.button3)
        buttonThree.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
        val buttonFour = findViewById<Button>(R.id.button4)
        buttonFour.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
        val buttonFive = findViewById<Button>(R.id.button5)
        buttonFive.setOnClickListener {
            finish()
        }
        val buttonSix = findViewById<Button>(R.id.button6)
        buttonSix.setOnClickListener {
            finishAffinity()
        }
    }
}