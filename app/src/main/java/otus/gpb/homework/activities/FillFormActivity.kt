package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : EditProfileActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val currentName = intent.extras?.getString("currentName")
        val editText = findViewById<EditText>(R.id.editText_name)
        editText.setText(currentName)



        val name = findViewById<EditText>(R.id.editText_name)
        val surname = findViewById<EditText>(R.id.editText_surname)
        val age = findViewById<EditText>(R.id.editText_age)

        findViewById<Button>(R.id.okButton).setOnClickListener {
            val intent = Intent().putExtra("user", UserProfile(name.text.toString(),surname.text.toString(), age.text.toString()))
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}