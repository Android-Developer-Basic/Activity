package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val editText = findViewById<EditText>(R.id.editText)
        val editText1 = findViewById<EditText>(R.id.editText1)
        val editText2 = findViewById<EditText>(R.id.editText2)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val first_name = getIntent().getStringExtra("first_name")
        editText.setText(first_name)
        val last_name = getIntent().getStringExtra("last_name")
        editText1.setText(last_name)
        val age = getIntent().getStringExtra("age")
        editText2.setText(age)
        saveButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("first_name", editText.text.toString())// - имя
            intent.putExtra("last_name"   , editText1.text.toString())// - фамилия
            intent.putExtra("age",  editText2.text.toString())// - возраст
           setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }
}