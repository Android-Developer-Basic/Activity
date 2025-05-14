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

class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_A)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.supportActionBar?.setTitle("ActivityB taskId=${this.taskId}")
        findViewById<Button>(R.id.button1_B).setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "call onRestart ActivityB")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "call onStart ActivityB")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "call onResume ActivityB")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "call onPause ActivityB")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "call onStop ActivityB")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "call onDestroy ActivityB")
    }

    override fun onNewIntent(intent : Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "call onNewIntent ActivityB")
    }
}