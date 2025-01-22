package otus.gpb.homework.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity: AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        editTextName = findViewById<EditText>(R.id.editText_name)
        editTextSurname = findViewById<EditText>(R.id.editText_surname)
        editTextAge = findViewById<EditText>(R.id.editText_age)

        val profile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(KEY_PROFILE, Profile::class.java)
        } else {
            intent.extras?.getParcelable<Profile>(KEY_PROFILE)
        }

        editTextName.setText(profile?.name)
        editTextSurname.setText(profile?.surname)
        editTextAge.setText(profile?.age)

        button = findViewById<Button>(R.id.button_apply)
        button.setOnClickListener {

            val resultName = editTextName.text.toString()
            val resultSurname = editTextSurname.text.toString()
            val resultAge = editTextAge.text.toString()

            val intent = Intent().putExtra(KEY_NAME, resultName)
            intent.putExtra(KEY_SURNAME, resultSurname)
            intent.putExtra(KEY_AGE, resultAge)

            setResult(RESULT_OK, intent)

            finish()
        }
    }
}