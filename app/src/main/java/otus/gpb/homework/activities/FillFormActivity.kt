package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editSurname: EditText
    private lateinit var editAge: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)
        editName = findViewById(R.id.name)
        editSurname = findViewById(R.id.surname)
        editAge = findViewById(R.id.age)
        sendButton = findViewById(R.id.button_send)

        val data = intent.getParcelableExtra<UserDTO>("data")
        if (data != null) {
            editName.setText(data.name)
            editSurname.setText(data.surname)
            editAge.setText(data.age.toString())
        }

        sendButton.setOnClickListener {
            val newData = UserDTO(
                name = editName.text.toString(),
                surname = editSurname.text.toString(),
                age = Integer.parseInt(editAge.text.toString())
            )
            setResult(Activity.RESULT_OK, Intent().apply { putExtra("data", newData) })
            finish()
        }
    }
}