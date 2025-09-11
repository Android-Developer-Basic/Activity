package otus.gpb.homework.activities

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

val TAG:String = "HW_06_LOG"
class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_a)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val startButtonB: Button = findViewById(R.id.startActB_button)

        startButtonB.setOnClickListener {
            val intentActivityB = Intent(this@ActivityA, ActivityB::class.java)
            startActivity(intentActivityB)
        }


    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "ActivityA onStart")
        Log.i(TAG, getTaskAffinity(this))
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ActivityA onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ActivityA onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "ActivityA onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "ActivityA onRestart")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "ActivityA onDestroy")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "ActivityA onNewIntent")
    }
}