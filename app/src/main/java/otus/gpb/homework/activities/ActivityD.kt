package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val TAG = "TAG"

class ActivityD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_d)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_D)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        this.supportActionBar?.setTitle("ActivityD taskId=${this.taskId}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "call onRestart ActivityD")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "call onStart ActivityD")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "call onResume ActivityD")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "call onPause ActivityD")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "call onStop ActivityD")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "call onDestroy ActivityD")
    }

    override fun onNewIntent(intent : Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "call onNewIntent ActivityD")
    }
}

