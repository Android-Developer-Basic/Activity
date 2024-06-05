package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {

    private var buttonOpenActivityA: Button? = null
    private var buttonOpenActivityD: Button? = null
    private var buttonCloseActivityC: Button? = null
    private var buttonCloseStack: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        title = getString(R.string.title_c)
        buttonOpenActivityA = findViewById(R.id.button_open_activity_a)
        buttonOpenActivityD = findViewById(R.id.button_open_activity_d)
        buttonCloseActivityC = findViewById(R.id.button_close_activity_c)
        buttonCloseStack = findViewById(R.id.button_close_stack)

        buttonOpenActivityA?.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        buttonOpenActivityD?.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        buttonCloseActivityC?.setOnClickListener {
            finish()
        }

        buttonCloseStack?.setOnClickListener {
            finishAffinity()
        }
    }
}