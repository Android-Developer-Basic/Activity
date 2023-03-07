package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : Activity() {

    private val keyDTO = "userDTO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(keyDTO, UserDTO::class.java)
        } else {
            intent.extras?.getParcelable(keyDTO)
        }

        val editName = findViewById<EditText>(R.id.edit_name)
        val editSurname = findViewById<EditText>(R.id.edit_surname)
        val editAge = findViewById<EditText>(R.id.edit_age)

        editName.setText(userData?.name)
        editSurname.setText(userData?.surname)
        editAge.setText(userData?.age)

        findViewById<Button>(R.id.button_apply).setOnClickListener {
            val intent = Intent()
            intent.putExtra(
                keyDTO, UserDTO(
                    editName.text.toString(),
                    editSurname.text.toString(),
                    editAge.text.toString()
                )
            )
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}