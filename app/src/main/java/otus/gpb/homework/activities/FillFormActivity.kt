package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    private lateinit var nameEdit: EditText
    private lateinit var secondNameEdit: EditText
    private lateinit var ageEdit: EditText
    private lateinit var applyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        nameEdit = findViewById(R.id.name_edit)
        secondNameEdit = findViewById(R.id.second_name_edit)
        ageEdit = findViewById(R.id.age_edit)
        applyButton = findViewById(R.id.apply_button)

        val data = intent.getParcelableExtra<UserDTO>("data")
        if (data != null) {
            nameEdit.setText(data.name)
            secondNameEdit.setText(data.secondName)
            ageEdit.setText(data.age.toString())
        }

        applyButton.setOnClickListener {
            val newData = UserDTO(
                name = nameEdit.text.toString(),
                secondName = secondNameEdit.text.toString(),
                age = Integer.parseInt(ageEdit.text.toString())
            )
            setResult(Activity.RESULT_OK, Intent().apply { putExtra("data", newData) })
            finish()
        }
    }
}
