package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

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

        val nameInput = findViewById<TextInputEditText>(R.id.editTextFirstName)

        val surnameInput = findViewById<TextInputEditText>(R.id.editTextLastName)

        val ageInput = findViewById<TextInputEditText>(R.id.editTextAge)

        nameInput.setText(intent.getStringExtra("name"))
        surnameInput.setText(intent.getStringExtra("surname"))
        ageInput.setText(intent.getStringExtra("age"))

        findViewById<Button>(R.id.button).setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("name", nameInput.text.toString())
                putExtra("surname", surnameInput.text.toString())
                putExtra("age", ageInput.text.toString())
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

}