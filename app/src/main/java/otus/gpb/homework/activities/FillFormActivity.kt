package otus.gpb.homework.activities

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : EditProfileActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        val inputName = findViewById<EditText>(R.id.inputName)
        val inputSurname = findViewById<EditText>(R.id.inputSurname)
        val inputAge = findViewById<EditText>(R.id.inputAge)

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent().apply {
                putExtra("user", User(inputName.text.toString(), inputSurname.text.toString(), inputAge.text.toString()))
                }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}