package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val KEY_NAME = "key_name"
const val KEY_SURNAME = "key_surname"
const val KEY_AGE = "key_age"

class FillFormActivity : AppCompatActivity() {
    private lateinit var valName: String
    private lateinit var valSurname: String
    private lateinit var valAge: String
    private var resultKey = RESULT_CANCELED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.buttonApply).setOnClickListener {
            valName = findViewById<EditText>(R.id.editTextName).text.toString()
            valSurname = findViewById<EditText>(R.id.editTextSurname).text.toString()
            valAge = findViewById<EditText>(R.id.editTextAge).text.toString()
            if (valName != "" || valSurname != "" || valAge != "") {
                resultKey = RESULT_OK
            }
            val intent = Intent().apply {
                putExtra(KEY_NAME, valName)
                putExtra(KEY_SURNAME, valSurname)
                putExtra(KEY_AGE, valAge)
            }
            setResult(resultKey, intent)
            finish()
        }
    }
}