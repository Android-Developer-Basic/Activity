package otus.gpb.homework.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import otus.gpb.homework.activities.User

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.button_send).setOnClickListener()
        {
            val user = User(findViewById<EditText>(R.id.name).text.toString(), findViewById<EditText>(R.id.lastName).text.toString(),
                findViewById<EditText>(R.id.age).text.toString())
            val intent = Intent().putExtra("outPut", user)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}