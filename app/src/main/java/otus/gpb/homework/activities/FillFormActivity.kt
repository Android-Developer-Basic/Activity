package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {

    private lateinit var acceptBtn : Button
    private lateinit var name_input : EditText
    private lateinit var surname_input : EditText
    private lateinit var age_input : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        acceptBtn = findViewById(R.id.accept_btn)
        name_input = findViewById(R.id.input_name)
        surname_input = findViewById(R.id.input_surname)
        age_input = findViewById(R.id.input_age)

        acceptBtn.setOnClickListener{

            val bundle = Bundle().apply {
                putString("name", name_input.text.toString())
                putString("surname", surname_input.text.toString())
                putString("age", age_input.text.toString())
            }

            val intent = Intent().apply {
                putExtra("data", bundle)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}