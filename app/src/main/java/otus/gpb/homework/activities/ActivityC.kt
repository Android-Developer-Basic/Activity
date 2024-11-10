package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {

    private var btnOpenActivityA    : Button? = null
    private var btnOpenActivityD    : Button? = null
    private var btnCloseActivityC   : Button? = null
    private var btnCloseStack       : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        btnOpenActivityA    = findViewById(R.id.btn_openActivityA)
        btnOpenActivityD    = findViewById(R.id.btn_openActivityD)
        btnCloseActivityC   = findViewById(R.id.btn_closeActivityC)
        btnCloseStack       = findViewById(R.id.btn_closeStack)

        btnOpenActivityA?.setOnClickListener {
            // По клику на кнопку “Open ActivityA” запустите ActivityA,
            // таким образом, чтобы мы попали на существующий экземпляр ActivityA
            // и у него был вызван метод onNewIntent, независимо от того находится
            // ActivityA наверху своего стека или нет

            // ActivityA is singleTask
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        btnOpenActivityD?.setOnClickListener {
            //По клику на кнопку “Open ActivityD” запустите ActivityD в том же стеке,
            // где расположены ActivityB и ActivityC, при этом завершите все предыдущие Activity,
            // которые находятся в текущем стеке

            val intent = Intent(this, ActivityD::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)          // не хавает
            startActivity(intent)
        }

        btnCloseActivityC?.setOnClickListener {
            // По клику на кнопку “CloseActivityC”, завершите ActivityC, и перейдите на предыдущий экран в стеке
            finish()
        }

        btnCloseStack?.setOnClickListener {
            // По клику на кнопку “Close Stack” завершите текущий стек, в котором находятся ActivityB и ActivityC,
            // и перейдите на ActivityA
            finishAndRemoveTask()
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart(){
        super.onStart()
        Log.i(TAG, "ActivityC onStart, taskID=$taskId")
    }

    override fun onStop(){
        super.onStop()
        Log.i(TAG, "ActivityC onStop, taskID=$taskId")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(TAG, "ActivityC onDestroy, taskID=$taskId")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ActivityC onPause, taskID=$taskId")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ActivityC onResume, taskID=$taskId")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(TAG, "ActivityC onNewIntent, taskID=$taskId")
    }
}