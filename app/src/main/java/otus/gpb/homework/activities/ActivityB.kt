package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityB : AppCompatActivity(R.layout.activity_b) {
    private val btnOpenActivityC by lazy {
        findViewById<Button>(R.id.btnOpenActivityC)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnOpenActivityC.setOnClickListener {  }
    }
}