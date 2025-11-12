package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityB : AppCompatActivity(), BaseComponentsI {
    override lateinit var buttonOpenView: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_b)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSupportActionBar()?.setTitle("Activity B")

        buttonOpenView = findViewById<ViewGroup>(R.id.base).findViewById(R.id.button)
        buttonOpenView.text = resources.getStringArray(R.array.open_buttons)[2] // Open Activity C
        buttonOpenView.setOnClickListener {
            startActivity( Intent(this, ActivityC::class.java))
        }
    }
}
