package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {

    private lateinit var acceptBtn : Button
    private lateinit var nameInput : EditText
    private lateinit var surnameInput : EditText
    private lateinit var ageInput : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        acceptBtn = findViewById(R.id.accept_btn)
        nameInput = findViewById(R.id.input_name)
        surnameInput = findViewById(R.id.input_surname)
        ageInput = findViewById(R.id.input_age)

        acceptBtn.setOnClickListener{

            val bundle = Bundle().apply {
                putString("name", nameInput.text.toString())
                putString("surname", surnameInput.text.toString())
                putString("age", ageInput.text.toString())
            }

            val intent = Intent().apply {
                putExtra("data", bundle)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}