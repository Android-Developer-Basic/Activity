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

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_a)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_A)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.supportActionBar?.setTitle("ActivityA taskId=${this.taskId}")
        findViewById<Button>(R.id.button1_A).setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "call onRestart ActivityA")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "call onStart ActivityA")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "call onResume ActivityA")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "call onPause ActivityA")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "call onStop ActivityA")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "call onDestroy ActivityA")
    }

    override fun onNewIntent(intent : Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "call onNewIntent ActivityA")
    }
}

