package otus.gpb.homework.activities

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "TAG"

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_C)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.supportActionBar?.setTitle("ActivityC taskId=${this.taskId}")
        findViewById<Button>(R.id.button1_C).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button2_C).setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button3_C).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.button4_C).setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finishAffinity()
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "call onRestart ActivityC")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "call onStart ActivityC")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "call onResume ActivityC")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "call onPause ActivityC")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "call onStop ActivityC")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "call onDestroy ActivityC")
    }

    override fun onNewIntent(intent : Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "call onNewIntent ActivityC")
    }
}