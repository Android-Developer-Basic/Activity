package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_fio)

        val button : Button = findViewById(R.id.button_edit_fio)
        button.setOnClickListener {
            val fname = findViewById<EditText>(R.id.plain_text_firstname).text.toString()
            val sname = findViewById<EditText>(R.id.plain_text_surname).text.toString()
            val age = findViewById<EditText>(R.id.plain_text_age).text.toString()
            val intent = Intent()

            if (fname.isNotBlank()) {
                intent.putExtra(RESULT_FIRSTNAME, fname)
            }
            if (sname.isNotBlank()) {
                intent.putExtra(RESULT_SURNAME, sname)
            }
            if (age.isNotBlank() && age.toIntOrNull() != null) {
                intent.putExtra(RESULT_AGE, age)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }
}