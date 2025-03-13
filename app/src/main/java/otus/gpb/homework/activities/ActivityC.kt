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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_c)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonOpenA = findViewById<Button>(R.id.button_open_activity_a)
        buttonOpenA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }



        val buttonOpenD = findViewById<Button>(R.id.button_open_activity_d)
        buttonOpenD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        val buttonCloseC = findViewById<Button>(R.id.button_close_activity_c)
        buttonCloseC.setOnClickListener {
            finish()
        }

        val buttonCloseStack = findViewById<Button>(R.id.button_close_stack)
        buttonCloseStack.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finishAffinity()
        }
    }
}