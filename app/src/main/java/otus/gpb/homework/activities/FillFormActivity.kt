package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val USER_INFO_KEY = "USER_INFO"

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.button_save).setOnClickListener() {
            saveButtonClick()
        }

        preFillingFields()
    }

    fun preFillingFields() {
        val userInfo = intent.extras?.getParcelable(USER_INFO_KEY) as UserInfo?
        findViewById<EditText>(R.id.edittext_name).setText(userInfo?.name)
        findViewById<EditText>(R.id.edittext_surname).setText(userInfo?.surname)
        findViewById<EditText>(R.id.edittext_age).setText(userInfo?.age)
    }

    fun saveButtonClick() {
        val userInfo = UserInfo(
            findViewById<EditText>(R.id.edittext_name).text.toString(),
            findViewById<EditText>(R.id.edittext_surname).text.toString(),
            findViewById<EditText>(R.id.edittext_age).text.toString(),
            null
        )
        setResult(RESULT_OK, Intent().putExtra(USER_INFO_KEY, userInfo))
        finish()
    }
}