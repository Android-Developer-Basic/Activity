package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {
    private lateinit var activityABtn: Button
    private lateinit var closeActivityCBtn: Button
    private lateinit var activityDBtn: Button
    private lateinit var activityStackBackBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityABtn = findViewById(R.id.buttonA)
        closeActivityCBtn = findViewById(R.id.buttonC)
        activityDBtn = findViewById(R.id.buttonD)
        activityStackBackBtn = findViewById(R.id.buttonBackStack)

        activityABtn.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }
        closeActivityCBtn.setOnClickListener {
            finish()
        }

        activityDBtn.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            startActivity(intent) }

        activityStackBackBtn.setOnClickListener {
            finishAndRemoveTask()
        }
    }


}