package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FillFormActivity : AppCompatActivity() {

    private val inputLastName by lazy{
        findViewById<EditText>(R.id.input_last_name)
    }
    private val inputFirstName by lazy{
        findViewById<EditText>(R.id.input_first_name)
    }
    private val inputAge by lazy{
        findViewById<EditText>(R.id.input_age)
    }
    private val okButton by lazy{
        findViewById<Button>(R.id.ok_button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fill_form_activity_layout)

        val manInfo:ManInfo = intent.getSerializableExtra("manInfo") as ManInfo
        if(manInfo != EditProfileActivity.DEFAULT_MAN_INFO){
            inputLastName.setText(manInfo.lastName)
            inputFirstName.setText(manInfo.firstName)
            inputAge.setText(manInfo.lastName)
        }
        okButton.setOnClickListener {
            val result  = ManInfo(
                inputLastName.text.toString(),
                inputFirstName.text.toString(),
                inputAge.text.toString().toInt()
            )
            val intent = Intent().putExtra("resultManInfo", result)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}