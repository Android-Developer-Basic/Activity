package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ActivityA : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        findViewById<Button>(R.id.bt_open_activity_b).setOnClickListener{
            startActivity(Intent(this,ActivityB::class.java))
        }
    }
}