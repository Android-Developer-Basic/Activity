package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ActivityA : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        title = resources.getString(R.string.app_name) + " : " + localClassName + ", task=$taskId"

        val buttonOpenActivityB = findViewById<Button>(R.id.openActivityB)
        buttonOpenActivityB.setOnClickListener{
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        title = resources.getString(R.string.app_name) + " : " + localClassName + ", task=$taskId"
        Toast.makeText(this, "Вызван метод onNewIntent",Toast.LENGTH_LONG).show()
    }
}
