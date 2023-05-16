package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        val buttonCloseMe = findViewById<Button>(R.id.close_activity_button)
        buttonCloseMe.setOnClickListener {
            Intent(this, ActivityC::class.java)
            this.finish()
        }
        val buttona = findViewById<Button>(R.id.a_activity_button)
        buttona.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }
        val buttond = findViewById<Button>(R.id.d_activity_button)
        buttond.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        val buttonCloseStack = findViewById<Button>(R.id.close_stack_button)
        buttonCloseStack.setOnClickListener {
            finishAffinity()
        }
    }
}