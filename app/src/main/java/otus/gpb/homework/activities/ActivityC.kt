package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_c)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // запустите ActivityA, таким образом, чтобы мы попали на существующий экземпляр ActivityA
        // и у него был вызван метод onNewIntent, независимо от того находится ActivityA наверху своего стека или нет
        // для этого у ActivityA задаем опцию android:launchMode="singleTask">
        val buttonOaa : Button = findViewById(R.id.button_oaa)
        buttonOaa.setOnClickListener {
            startActivity(Intent(this, ActivityA::class.java))
        }

        // По клику на кнопку “Open ActivityD” запустите ActivityD в том же стеке, где расположены ActivityB и ActivityC,
        // при этом завершите все предыдущие Activity, которые находятся в текущем стеке
        val buttonOad : Button = findViewById(R.id.button_oad)
        buttonOad.setOnClickListener {
            val intent = Intent(this, ActivityD::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)  // перед запуском Activity, очистить существующий уже таск с таким же taskAffinity
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)    // работает только в связке c Intent.FLAG_ACTIVITY_NEW_TASK
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        // По клику на кнопку “CloseActivityC”, завершите ActivityC, и перейдите на предыдущий экран в стеке
        val buttonCac : Button = findViewById(R.id.button_cac)
        buttonCac.setOnClickListener {
            finish() // завершить работу текущего Activity
        }

        // По клику на кнопку “Close Stack” завершите текущий стек, в котором находятся ActivityB и ActivityC, и перейдите на ActivityA
        val buttonCs : Button = findViewById(R.id.button_cs)
        buttonCs.setOnClickListener {
            finishAffinity()  //  завершить работу всех Activity в текущем таске, у которых совпадает taskAffinity с текущим Activity
        }
    }
}