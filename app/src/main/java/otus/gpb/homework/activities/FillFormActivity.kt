package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val nameInput = findViewById<EditText>(R.id.edit_name)
        val surnameInput = findViewById<EditText>(R.id.edit_surname)
        val ageInput = findViewById<EditText>(R.id.edit_age)
        val buttonApply = findViewById<Button>(R.id.button_apply)


        buttonApply.setOnClickListener {
            val data = Intent().apply {
                putExtra("name", nameInput.text.toString())
                putExtra("surname", surnameInput.text.toString())
                putExtra("age", ageInput.text.toString())
            }
            setResult(RESULT_OK, data)
            finish()

        }
    }
}