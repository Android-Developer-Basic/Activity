package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.appy).setOnClickListener {
            UserInfo(
                name = findViewById<EditText>(R.id.editName).text.toString(),
                surName = findViewById<EditText>(R.id.editSurname).text.toString(),
                age = findViewById<EditText>(R.id.editAge).text.toString().toIntOrNull()
            ).let { userInfo ->
                Intent().putExtra(RESULT_KEY, userInfo).let { intent ->
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    companion object {
        const val RESULT_KEY = "fillFormKey"
    }
}