package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var applyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        nameEditText = findViewById(R.id.edittext_name)
        surnameEditText = findViewById(R.id.edittext_surname)
        ageEditText = findViewById(R.id.edittext_age)
        applyButton = findViewById(R.id.button_apply)

        applyButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val surname = surnameEditText.text.toString().trim()
            val age = ageEditText.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || age.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Формируем результат
            val resultIntent = Intent().apply {
                putExtra("name", name)
                putExtra("surname", surname)
                putExtra("age", age)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}