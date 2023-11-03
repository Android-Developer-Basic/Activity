package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityC : AppCompatActivity(R.layout.activity_c) {

    private val btnOpenActivityA by lazy {
        findViewById<Button>(R.id.btnOpenActivityA)
    }
    private val btnOpenActivityD by lazy {
        findViewById<Button>(R.id.btnOpenActivityD)
    }
    private val btnCloseActivityC by lazy {
        findViewById<Button>(R.id.btnCloseActivityC)
    }
    private val btnCloseStack by lazy {
        findViewById<Button>(R.id.btnCloseStack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnOpenActivityA.setOnClickListener {  }
        btnOpenActivityD.setOnClickListener {  }
        btnCloseActivityC.setOnClickListener {  }
        btnCloseStack.setOnClickListener {  }
    }
}