package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity: AppCompatActivity(R.layout.activity_fill_form) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val editName = findViewById<EditText>(R.id.editName)
        val editSurname = findViewById<EditText>(R.id.editSurname)
        val editAge = findViewById<EditText>(R.id.editAge)

        findViewById<Button>(R.id.buttonApply).setOnClickListener {
                intent = Intent()
                    .putExtra("name", editName.text.toString())
                    .putExtra("surname", editSurname.text.toString())
                    .putExtra("age", editAge.text.toString())
                setResult(RESULT_OK, intent)
                finish()
        }
    }
}