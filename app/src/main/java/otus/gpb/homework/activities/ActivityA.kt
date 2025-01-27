package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_a)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonOab : Button = findViewById(R.id.button_oab)  // кнопка запуска ActivityB
        val cbNewTask : CheckBox = findViewById(R.id.cbNewTask)  // переключатель новый таск/текущий таск
        // По клику на кнопку “Open ActivityB” запустите ActivityB в отдельном стеке
        buttonOab.setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            //  при этом предусмотрите возможность открывать другие Activity в том же стеке где расположена ActivityA
            if (cbNewTask.isChecked) {
                // новый таск
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            }
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Toast.makeText(this@ActivityA, getString(R.string.OnNewIntent), Toast.LENGTH_SHORT).show()
    }
}