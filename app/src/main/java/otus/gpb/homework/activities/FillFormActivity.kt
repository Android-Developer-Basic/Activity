package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.EditProfileActivity.Companion.TEXT_VIEW_AGE
import otus.gpb.homework.activities.EditProfileActivity.Companion.TEXT_VIEW_NAME
import otus.gpb.homework.activities.EditProfileActivity.Companion.TEXT_VIEW_SUR_NAME

class FillFormActivity : AppCompatActivity() {

    private lateinit var textviewName: EditText
    private lateinit var textviewSurName: EditText
    private lateinit var textviewAge: EditText
    private lateinit var buttonApply: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        textviewName = findViewById(R.id.textview_name)
        textviewSurName = findViewById(R.id.textview_surname)
        textviewAge = findViewById(R.id.textview_age)
        buttonApply = findViewById(R.id.button_apply)

        buttonApply.setOnClickListener {
            Log.d("textviewName.text", textviewName.text.toString())
            apply()
        }
    }


    private fun apply() {
        val intent = Intent()
        intent.putExtra(TEXT_VIEW_NAME, textviewName.text.toString())
        intent.putExtra(TEXT_VIEW_SUR_NAME, textviewSurName.text.toString())
        intent.putExtra(TEXT_VIEW_AGE, textviewAge.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }

}