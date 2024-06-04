package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class FillFormActivity : AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        findViewById<Button>(R.id.button5).setOnClickListener {
            val profile = Profile(
                findViewById<EditText>(R.id.edittextview_name).text.toString(),
                findViewById<EditText>(R.id.edittextview_surname).text.toString(),
                findViewById<EditText>(R.id.edittextview_age).text.toString()
            )
            val intent = Intent().putExtra(RESULT_KEY, profile)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}