package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FillFormActivity : AppCompatActivity() {

    private lateinit var editTextName:EditText
    private lateinit var editTextLastName:EditText
    private lateinit var editTextAge:EditText
    private lateinit var buttonAccept: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        editTextName = findViewById(R.id.editTextName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextAge = findViewById(R.id.editTextAge)
        buttonAccept = findViewById(R.id.buttonAccept)

        buttonAccept.setOnClickListener() {
            if (editTextName.text.isEmpty() || editTextLastName.text.isEmpty() || editTextAge.text.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG ).show()
            }
            else {
                setResult(RESULT_OK, Intent().
                putExtra(EditProfileActivity.FIRST_NAME, editTextName.text.toString()).
                putExtra(EditProfileActivity.LAST_NAME, editTextLastName.text.toString()).
                putExtra(EditProfileActivity.AGE, editTextAge.text.toString()))
                finish()
            }
        }
    }
}