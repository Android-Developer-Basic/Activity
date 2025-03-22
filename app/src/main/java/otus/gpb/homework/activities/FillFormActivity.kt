package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.EditProfileActivity.ProfileOptions.ProfileInfo

class FillFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editAge: EditText = findViewById(R.id.edit_text_age)
        val editLastName: EditText = findViewById(R.id.edit_text_last_name)
        val editFirstName: EditText = findViewById(R.id.edit_text_first_name)

        @Suppress("DEPRECATION") val profileInfo =
            intent.extras?.getSerializable(ContractFillProfile.KEY_FILL_PROFILE) as ProfileInfo
        editLastName.setText(profileInfo.surname)
        editFirstName.setText(profileInfo.name)
        editAge.setText(profileInfo.age.toString())

        val buttonApply = findViewById<Button>(R.id.button_apply)
        buttonApply.setOnClickListener {
            val result = ProfileInfo(
                name = editFirstName.text.toString(),
                surname = editLastName.text.toString(),
                age = editAge.text.toString().toInt()
            )
            val intent = Intent()
            intent.putExtra(ContractFillProfile.KEY_RESULT_FILL_PROFILE, result)
            setResult(RESULT_OK, intent)
            finish()
        }
    }


}