package otus.gpb.homework.activities

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.widget.Button

class ActivityC : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        title = resources.getString(R.string.app_name) + " : " + localClassName + ", task=$taskId"

        val buttonOpenActivityA = findViewById<Button>(R.id.openActivityA)
        buttonOpenActivityA.setOnClickListener{
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        val buttonOpenActivityD = findViewById<Button>(R.id.openActivityD)
        buttonOpenActivityD.setOnClickListener{
            val intent = Intent(this, ActivityD::class.java)
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val buttonCloseActivityC = findViewById<Button>(R.id.closeActivityC)
        buttonCloseActivityC.setOnClickListener{
            this.finish()
        }

        val buttonCloseStack = findViewById<Button>(R.id.closeStack)
        buttonCloseStack.setOnClickListener{
            finishAffinity()
        }
    }
}
