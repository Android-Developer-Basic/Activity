package ru.margarita.homework_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import otus.gpb.homework.activities.R


class ActivityC: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)
        val buttonOpenA = findViewById<Button>(R.id.buttonOpenA)
        val buttonOpenD = findViewById<Button>(R.id.buttonOpenD)
        val buttonCloseC = findViewById<Button>(R.id.buttonCloseC)
        val buttonCloseStack = findViewById<Button>(R.id.buttonCloseStack)

        buttonOpenA.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

        // По клику на кнопку “Open ActivityD” запустите ActivityD в том же стеке, где расположены ActivityB и ActivityC, при этом завершите все предыдущие Activity, которые находятся в текущем стеке
        buttonOpenD.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            startActivity(intent)

        }

        // По клику на кнопку “CloseActivityC”, завершите ActivityC, и перейдите на предыдущий экран в стеке
        buttonCloseC.setOnClickListener {
            finish();
        }

        //По клику на кнопку “Close Stack” завершите текущий стек, в котором находятся ActivityB и ActivityC, и перейдите на ActivityA
        buttonCloseStack.setOnClickListener {
            finishAffinity()
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)

        }
    }
}