package otus.gpb.homework.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ActivityD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_d)
        if (isTaskRoot) {
            Log.d(TAG, "Activity D is root")
        }
    }

    companion object {
        const val TAG = "Activity D"
    }
}