package otus.gpb.homework.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityD : AppCompatActivity() {
    lateinit var textTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_d)
        textTV = findViewById(R.id.textTV)
        textTV.text = ActivityUtils.getTaskAffinity(this)
    }
}