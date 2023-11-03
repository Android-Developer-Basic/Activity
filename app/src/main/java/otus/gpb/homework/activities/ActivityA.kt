package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.reflect.KProperty

class ActivityA : AppCompatActivity(R.layout.activity_a) {

    private val btnOpenActivityB by lazy {
        findViewById<Button>(R.id.btnOpenActivityB)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnOpenActivityB.setOnClickListener {  }
    }
}
