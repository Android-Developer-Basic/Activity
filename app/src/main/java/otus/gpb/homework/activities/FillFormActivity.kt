package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        val editTextName = findViewById<EditText>(R.id.Name)
        val editTextSurname = findViewById<EditText>(R.id.Surname)
        val editTextAge = findViewById<EditText>(R.id.Age)
        val buttonApply = findViewById<Button>(R.id.result_info)
        buttonApply.setOnClickListener {
            val name = editTextName.text.toString()
            val surname = editTextSurname.text.toString()
            val age = editTextAge.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("name",name)
            resultIntent.putExtra("surname",surname)
            resultIntent.putExtra("age",age)
            setResult(Activity.RESULT_OK,resultIntent)
            finish()
        }
    }
}
