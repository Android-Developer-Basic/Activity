package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class FillFormActivity : AppCompatActivity() {

    private lateinit var editTextName: TextView
    private lateinit var editTextSurname: TextView
    private lateinit var editTextAge: TextView
    private lateinit var buttonApply: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        editTextName = findViewById(R.id.edit_text_name)
        editTextSurname = findViewById(R.id.edit_text_surname)
        editTextAge = findViewById(R.id.edit_text_age)
        buttonApply = findViewById(R.id.button_apply)

        editTextName.text = intent.getStringExtra("name")
        editTextSurname.text = intent.getStringExtra("surname")
        editTextAge.text = intent.getStringExtra("age")

        buttonApply.setOnClickListener {
            val name = editTextName.text.toString()
            val surname = editTextSurname.text.toString()
            val age = editTextAge.text.toString()

            val intent = Intent().apply {
                putExtra("name", name)
                putExtra("surname", surname)
                putExtra("age", age)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}