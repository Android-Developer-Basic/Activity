package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.buttonApply).setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val surname = findViewById<EditText>(R.id.editTextSurname).text.toString()
            val age = findViewById<EditText>(R.id.editTextAge).text.toString()
            val intent = Intent().putExtra("result", User(name,surname,age))
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
