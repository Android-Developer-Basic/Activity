package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_activity)
        findViewById<Button>(R.id.activityAButton).setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        Log.d(TAG, "onNewIntent callback")
        super.onNewIntent(intent)
    }

    companion object {
        const val TAG = "Activity A"
    }
}