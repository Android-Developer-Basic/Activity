package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextSurName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var buttonAccept: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        editTextName = findViewById(R.id.editTextName)
        editTextSurName = findViewById(R.id.editTextSurName)
        editTextAge = findViewById(R.id.editTextYear)
        buttonAccept = findViewById(R.id.acceptButton)

        buttonAccept.setOnClickListener {
            if (editTextName.text.isEmpty() || editTextSurName.text.isEmpty() || editTextAge.text.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG ).show()
            }
            else {
                val data = FormData(
                    name = editTextName.text.toString(),
                    surName = editTextSurName.text.toString(),
                    year = editTextAge.text.toString()
                )

                setResult(RESULT_OK, Intent().
                putExtra("data", data))
                finish()
            }
        }


    }
}