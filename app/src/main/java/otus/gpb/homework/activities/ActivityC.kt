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

        findViewById<Button>(R.id.buttonToAFromC).setOnClickListener {
            startActivity(Intent(this,ActivityA::class.java))
        }

        findViewById<Button>(R.id.buttonToDFromC).setOnClickListener {
            startActivity(Intent(this,ActivityD::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        findViewById<Button>(R.id.buttonCloseC).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.buttonCloseStack).setOnClickListener {
            finishAffinity()
        }


    }
}