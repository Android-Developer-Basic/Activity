package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_d)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart(){
        super.onStart()
        Log.i(TAG, "ActivityD onStart, taskID=$taskId")
    }

    override fun onStop(){
        super.onStop()
        Log.i(TAG, "ActivityD onStop, taskID=$taskId")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(TAG, "ActivityD onDestroy, taskID=$taskId")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ActivityD onPause, taskID=$taskId")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ActivityD onResume, taskID=$taskId")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "ActivityD onNewIntent, taskID=$taskId")
    }
}