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

        val name = findViewById<EditText>(R.id.nameEditText)
        val lastName = findViewById<EditText>(R.id.lastNameEditText)
        val age = findViewById<EditText>(R.id.ageEditText)


        val applyButton = findViewById<Button>(R.id.apply_button)
        applyButton.setOnClickListener {
            val i = Intent()
            i.putExtra("name", name.text.toString())
            i.putExtra("lastName", lastName.text.toString())
            i.putExtra("age", age.text.toString())
            setResult(RESULT_OK, i)
            finish()
        }
    }

}