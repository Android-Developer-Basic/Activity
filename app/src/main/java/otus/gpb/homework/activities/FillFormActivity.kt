package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val NAME_KEY = "namekey"
const val SURNAME_KEY = "surnamekey"
const val AGE_KEY = "agekey"

class FillFormActivity : AppCompatActivity() {
    private lateinit var varName: String
    private lateinit var varSurname: String
    private lateinit var varAge: String
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

        var buttonApply = findViewById<Button>(R.id.buttonApply).setOnClickListener {
            varName = findViewById<EditText>(R.id.editTextName).text.toString()
            varSurname = findViewById<EditText>(R.id.editTextSurname).text.toString()
            varAge = findViewById<EditText>(R.id.editTextAge).text.toString()
            if (varName != "" || varSurname != "" || varAge != "") {
                resultKey = RESULT_OK
            }
            val intent = Intent().apply {
                putExtra(NAME_KEY, varName)
                putExtra(SURNAME_KEY, varSurname)
                putExtra(AGE_KEY, varAge)
            }
            setResult(resultKey, intent)
            finish()
        }
    }
}