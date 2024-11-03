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

        val activityAButton = findViewById<Button>(R.id.activity_a_button)
        val activityDButton = findViewById<Button>(R.id.activity_d_button)
        val closeActivityCButton = findViewById<Button>(R.id.close_activity_c_button)
        val closeStackButton = findViewById<Button>(R.id.close_stack_button)

        activityAButton.setOnClickListener {
            val activityA = Intent(this, ActivityA::class.java)
            activityA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(activityA)
        }

        activityDButton.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        closeActivityCButton.setOnClickListener {
            finish()
        }

        closeStackButton.setOnClickListener {
            finishAffinity()
        }
    }
}