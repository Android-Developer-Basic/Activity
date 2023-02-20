package otus.gpb.homework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val name = findViewById<EditText>(R.id.edit_name)
        val surname = findViewById<EditText>(R.id.edit_surname)
        val age = findViewById<EditText>(R.id.edit_age)
        val saveChangesButton = findViewById<Button>(R.id.save_changes_button)
    }
}