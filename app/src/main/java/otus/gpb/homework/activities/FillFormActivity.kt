package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fill_form_for_profile)

        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextAge = findViewById(R.id.editTextAge)

        val applyButton = findViewById<Button>(R.id.button)
        applyButton.setOnClickListener {
            val intent = Intent().apply {
                putExtra("name", editTextName.text.toString())
                putExtra("surname", editTextSurname.text.toString())
                putExtra("age", editTextAge.text.toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}