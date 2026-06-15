package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val nameEditText = findViewById<EditText>(R.id.edittext_name)
        val surnameEditText = findViewById<EditText>(R.id.edittext_surname)
        val ageEditText = findViewById<EditText>(R.id.edittext_age)

        findViewById<Button>(R.id.button_apply).setOnClickListener {
            val result = Intent().apply {
                putExtra(EXTRA_NAME, nameEditText.text.toString())
                putExtra(EXTRA_SURNAME, surnameEditText.text.toString())
                putExtra(EXTRA_AGE, ageEditText.text.toString())
            }
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_SURNAME = "extra_surname"
        const val EXTRA_AGE = "extra_age"
    }
}
