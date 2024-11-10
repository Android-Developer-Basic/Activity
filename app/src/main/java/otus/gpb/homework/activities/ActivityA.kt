package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val TAG = "TAG"

class ActivityA : AppCompatActivity() {

    private var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_a)
        Log.i(TAG, "call onCreate")
        button = findViewById(R.id.btn_openActivityB)
        button?.setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart(){
        super.onStart()
        Log.i(TAG, "ActivityA onStart, taskID=$taskId")
    }

    override fun onStop(){
        super.onStop()
        Log.i(TAG, "ActivityA onStop, taskID=$taskId")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(TAG, "ActivityA onDestroy, taskID=$taskId")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ActivityA onPause, taskID=$taskId")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ActivityA onResume, taskID=$taskId")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "ActivityA onNewIntent, taskID=$taskId")
    }
}