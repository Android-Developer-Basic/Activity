package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.UserDTO

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val name = findViewById<EditText>(R.id.edit_name)
        val surname = findViewById<EditText>(R.id.edit_surname)
        val age = findViewById<EditText>(R.id.edit_age)

        val button = findViewById<Button>(R.id.button_apply).setOnClickListener {
            val intent = Intent().apply{
                val user = UserDTO (name.text.toString(),surname.text.toString(),age.text.toString())
                putExtra("UserInfo", user)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this).apply  {
            setTitle(R.string.backpressed_title)
            setMessage(R.string.backpressed_message)
            setPositiveButton(R.string.backpressed_exit) { _, _ ->
                super.onBackPressed()
            }
            setNegativeButton(R.string.cancel_permission, null)
            show()
        }
    }
}