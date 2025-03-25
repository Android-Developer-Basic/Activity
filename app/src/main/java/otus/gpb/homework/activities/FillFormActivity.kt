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
    companion object {
        const val KEY_NAME = "key_name"
        const val KEY_SURNAME = "key_surname"
        const val KEY_AGE = "key_age"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextSurname = findViewById<EditText>(R.id.editTextSurname)
        val editTextAge = findViewById<EditText>(R.id.editTextAge)

        if (intent.extras != null) {
            editTextName.setText(intent.extras?.getString(KEY_NAME, ""))
            editTextSurname.setText(intent.extras?.getString(KEY_SURNAME, ""))
            editTextAge.setText(intent.extras?.getString(KEY_AGE, ""))
        }

        findViewById<Button>(R.id.buttonApply).apply {
            setOnClickListener {
                val name = editTextName.text.toString()
                val surname = editTextSurname.text.toString()
                val age = editTextAge.text.toString()

                val intent = Intent().apply {
                    putExtra(KEY_NAME, name)
                    putExtra(KEY_SURNAME, surname)
                    putExtra(KEY_AGE, age)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}