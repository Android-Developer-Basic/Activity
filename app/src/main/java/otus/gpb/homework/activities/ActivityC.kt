package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        val openActivityA: Button = findViewById(R.id.btn_open_activity_a)
        openActivityA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val openActivityD: Button = findViewById(R.id.btn_open_activity_d)
        openActivityD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        val closeActivityC: Button = findViewById(R.id.btn_close_activity_c)
        closeActivityC.setOnClickListener {
            finish()
        }

        val closeStack: Button = findViewById(R.id.btn_close_stack)
        closeStack.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}