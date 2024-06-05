package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val editFirstName: EditText = findViewById(R.id.edit_first_name)
        val editLastName: EditText = findViewById(R.id.edit_last_name)
        val editAge: EditText = findViewById(R.id.edit_age)

        val btnOk: Button = findViewById(R.id.btn_ok)
        btnOk.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra(USER_ID, UserProfileInfo(
                    firstName = editFirstName.text.toString(),
                    lastName = editLastName.text.toString(),
                    age = editAge.text.toString().toInt(),
                ))
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        val btnCancel: Button = findViewById(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            finish()
        }
    }
}