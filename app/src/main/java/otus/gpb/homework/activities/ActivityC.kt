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
        val btnOpenA = findViewById<Button>(R.id.btn_Open_A)
        btnOpenA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        val btnOpenD = findViewById<Button>(R.id.btn_Open_D)
        btnOpenD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        val btncloseC = findViewById<Button>(R.id.btn_close_C)
        btncloseC.setOnClickListener {
            finish()
        }
        val btncloseStack = findViewById<Button>(R.id.btn_close_Stack)
        btncloseStack.setOnClickListener {
            finishAffinity()
            finishAndRemoveTask()
        }
    }
}