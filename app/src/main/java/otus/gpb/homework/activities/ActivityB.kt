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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val startButtonC: Button = findViewById(R.id.startActC_button)

        startButtonC.setOnClickListener{
            val intentActivityC = Intent( this@ActivityB, ActivityC::class.java)
            startActivity(intentActivityC)
        }

    }
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "ActivityB onStart")
        Log.i( TAG, getTaskAffinity(this) )
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ActivityB onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ActivityB onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "ActivityB onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "ActivityB onRestart")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "ActivityB onDestroy")
    }

}