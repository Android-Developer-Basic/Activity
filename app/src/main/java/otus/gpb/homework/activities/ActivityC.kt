package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {
    private lateinit var btn1:Button // Open A
    private lateinit var btn2:Button // Open D
    private lateinit var btn3:Button // Close C
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getSupportActionBar()?.setTitle("Activity C")

        btn1 = findViewById<ViewGroup>(R.id.base_1).findViewById(R.id.button)
        btn1.text = resources.getStringArray(R.array.open_buttons)[0] // Open Activity A
        btn1.setOnClickListener { startActivity(Intent(this, ActivityA::class.java)) }

        btn2 = findViewById<ViewGroup>(R.id.base_2).findViewById(R.id.button)
        btn2.text = resources.getStringArray(R.array.open_buttons)[3] // Open Activity D
        btn2.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        btn3 = findViewById<ViewGroup>(R.id.base_3).findViewById(R.id.button)
        btn3.text = resources.getStringArray(R.array.close_buttons)[0] // Close Activity C
        btn3.setOnClickListener { finish() }

    }
}
