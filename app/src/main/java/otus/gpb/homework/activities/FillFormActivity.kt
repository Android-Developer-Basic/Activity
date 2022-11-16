package otus.gpb.homework.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import otus.gpb.homework.activities.dto.User

class FillFormActivity : AppCompatActivity() {

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var age: EditText

    // no idea why it warns last_Name id as absent
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_Name)
        age = findViewById(R.id.age)

        findViewById<Button>(R.id.send_btn).apply {
            setOnClickListener {
                Intent().apply {
                    val user = User(
                        firstName.text.toString(),
                        lastName.text.toString(),
                        age.text.toString()
                    )
                    putExtra(CustomActivityResultContract.USER_DTO_EXTRA, user)
                }
            }
        }
    }
}