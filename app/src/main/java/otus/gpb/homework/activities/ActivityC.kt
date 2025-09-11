package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val startButtonA: Button = findViewById(R.id.startActA_button)
        val startButtonD: Button = findViewById(R.id.startActD_button)
        val stopButtonC:  Button = findViewById(R.id.stopActC_button)
        val stopButtonStack:  Button = findViewById(R.id.stopApp_button)

        startButtonA.setOnClickListener{
            val intentActivityA = Intent( this@ActivityC, ActivityA::class.java)
            startActivity(intentActivityA)
        }
        startButtonD.setOnClickListener{
            val intentActivityD = Intent( this@ActivityC, ActivityD::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intentActivityD)
        }

        stopButtonStack.setOnClickListener{
            finishAffinity()
        }

        stopButtonC.setOnClickListener{
            finish()
        }


    }
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "ActivityC onStart")
        Log.i( TAG, getTaskAffinity(this) )
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ActivityC onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ActivityC onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "ActivityC onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "ActivityC onRestart")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "ActivityC onDestroy")
    }

}