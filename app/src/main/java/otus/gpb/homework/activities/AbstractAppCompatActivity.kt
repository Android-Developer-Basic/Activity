package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractAppCompatActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("debug_activity", "${logInfo()}: onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.i("debug_activity", "${logInfo()}: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("debug_activity", "${logInfo()}: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("debug_activity", "${logInfo()}: onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("debug_activity", "${logInfo()}: onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("debug_activity", "${logInfo()}: onDestroy")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i("debug_activity", "${logInfo()}: onNewIntent")
    }
}
