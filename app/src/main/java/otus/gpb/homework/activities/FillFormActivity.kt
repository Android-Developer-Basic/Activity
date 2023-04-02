package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import otus.gpb.homework.activities.ContractEditProfileAndFillForm.Companion.KEY

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val person :PersonDTO? = intent.extras?.getParcelable(KEY)

        val editNameView = findViewById<EditText>(R.id.edit_name)
        val editSurnameView = findViewById<EditText>(R.id.edit_surname)
        val editAgeView = findViewById<EditText>(R.id.edit_age)
        val editButton = findViewById<Button>(R.id.apply_button)

            editNameView.setText(person?.name)
            editSurnameView.setText(person?.surname)
            editAgeView.setText(person?.age)


        editButton.setOnClickListener {
            val intent = Intent().putExtra(KEY,
                PersonDTO(
                    editNameView.text.toString(),
                    editSurnameView.text.toString(),
                    editAgeView.text.toString()))
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}