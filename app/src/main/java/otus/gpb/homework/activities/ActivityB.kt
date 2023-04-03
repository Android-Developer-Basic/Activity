package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ActivityB : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        title = resources.getString(R.string.app_name) + " : " + localClassName + ", task=$taskId"

        val buttonOpenActivityC = findViewById<Button>(R.id.openActivityC)
        buttonOpenActivityC.setOnClickListener{
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)
        }
    }
}
