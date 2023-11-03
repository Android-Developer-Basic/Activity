package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity(R.layout.activity_c) {

    private val btnOpenActivityA by lazy {
        findViewById<Button>(R.id.btnOpenActivityA)
    }
    private val btnOpenActivityD by lazy {
        findViewById<Button>(R.id.btnOpenActivityD)
    }
    private val btnCloseActivityC by lazy {
        findViewById<Button>(R.id.btnCloseActivityC)
    }
    private val btnCloseStack by lazy {
        findViewById<Button>(R.id.btnCloseStack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Activity C"

        btnOpenActivityA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        btnOpenActivityD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        btnCloseActivityC.setOnClickListener {
            this.finish()
        }

        btnCloseStack.setOnClickListener {
            finishAffinity()
        }
    }
}