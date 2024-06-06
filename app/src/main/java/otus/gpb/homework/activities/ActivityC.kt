package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {

    private var button: Button? = null
    private var buttonA: Button? = null
    private var buttonCloseC: Button? = null
    private var buttonCloseStack: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)

        button = findViewById(R.id.button3)
        button?.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NO_HISTORY

            startActivity(intent)
        }

        buttonA = findViewById(R.id.buttonA)
        buttonA?.setOnClickListener{
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        buttonCloseC = findViewById(R.id.buttonClose)
        buttonCloseC?.setOnClickListener{
            finish()
        }

        buttonCloseStack = findViewById(R.id.buttonCloseStack)
        buttonCloseStack?.setOnClickListener{
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
            finishAffinity()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}