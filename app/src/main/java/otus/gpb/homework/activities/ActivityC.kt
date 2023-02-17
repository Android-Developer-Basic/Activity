package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ActivityC : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        findViewById<Button>(R.id.bt_open_activity_d).setOnClickListener{
            val intent = Intent(this, ActivityD::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        findViewById<Button>(R.id.bt_open_activity_a).setOnClickListener{
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.bt_close_activity_c).setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.bt_close_stack).setOnClickListener{
            finishAndRemoveTask()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}