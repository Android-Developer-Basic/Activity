package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ActivityB : AppCompatActivity() {

    private var btnOpenActivityC: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b)
        btnOpenActivityC = findViewById(R.id.btn_openActivityC)
        btnOpenActivityC?.setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
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
        Log.i(TAG, "ActivityB onStart, taskID=$taskId")
    }

    override fun onStop(){
        super.onStop()
        Log.i(TAG, "ActivityB onStop, taskID=$taskId")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(TAG, "ActivityB onDestroy, taskID=$taskId")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ActivityB onPause, taskID=$taskId")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ActivityB onResume, taskID=$taskId")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "ActivityB onNewIntent, taskID=$taskId")
    }
}