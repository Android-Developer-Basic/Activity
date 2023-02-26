package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.utils.PersonDTO

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val firstName = findViewById<EditText>(R.id.firstName)
        val lastName = findViewById<EditText>(R.id.lastName)
        val age = findViewById<EditText>(R.id.age)

        val applyButton = findViewById<Button>(R.id.button_apply)
        applyButton.setOnClickListener {

            if (TextUtils.isEmpty(firstName.text)) {
                Toast.makeText(
                    this,
                    getString(R.string.person_first_name_required),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(lastName.text)) {
                Toast.makeText(
                    this,
                    getString(R.string.person_last_name_required),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(age.text)) {
                Toast.makeText(
                    this,
                    getString(R.string.person_age_name_required),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val intent = Intent()

            intent.putExtra(
                "personDto", PersonDTO(
                    firstName.text.toString(), lastName.text.toString(), age.text.toString().toInt()
                )
            )

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun makeRequired(field: EditText, text: Int) {
        if (TextUtils.isEmpty(field.text)) {
            Toast.makeText(this, getString(text), Toast.LENGTH_LONG).show()
            return
        }
    }
}