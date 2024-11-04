package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        findViewById<Button>(R.id.activityCButton).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        findViewById<Button>(R.id.activityCButton2).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.activityCButton3).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.activityCButton4).setOnClickListener {
            finishAffinity()
        }
    }
}
