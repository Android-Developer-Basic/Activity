package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ActivityA : AppCompatActivity() {

    private var buttonOpenActivityB: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_a)
        title = getString(R.string.title_a)
        buttonOpenActivityB = findViewById(R.id.button_open_activity_b)

        buttonOpenActivityB?.setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}