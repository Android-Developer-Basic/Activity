package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler.Callback
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityB : AbstractAppCompatActivity() {

    companion object FinishHandler {
        lateinit var finishAndRemoveTaskHCallback: Callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        finishAndRemoveTaskHCallback = Callback {
            (it.what == 0).apply { if (this) finishAndRemoveTask() }
        }

        findViewById<Button>(R.id.button_open_activity_c).setOnClickListener {
            val intentActivityC = Intent(this, ActivityC::class.java)
            startActivity(intentActivityC)
        }
    }
}
