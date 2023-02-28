package otus.gpb.homework.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FillFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val name = findViewById<EditText>(R.id.edit_name)
        name.setText(intent.extras?.getString("name"))
        val surname = findViewById<EditText>(R.id.edit_surname)
        surname.setText(intent.extras?.getString("surname"))
        val age = findViewById<EditText>(R.id.edit_age)
        age.setText(intent.extras?.getString("age"))
        val saveChangesButton = findViewById<Button>(R.id.save_changes_button)
        saveChangesButton.setOnClickListener{sendData(name, surname, age) }
    }

    private fun sendData(name: EditText, surname: EditText, age: EditText){
        val ageString = age.text.toString()
        if(ageString != ""){
            try{
                val ageNum = ageString.toInt()
                if(ageNum < 0) ageNum * (-1)
                agePostfix = if(ageNum in 11..19 || (ageNum % 100) in 11..19) " лет."
                else {
                    when (ageNum % 10) {
                        1 -> " год."
                        in 2..4 -> " года."
                        else -> " лет."
                    }
                }

            }catch (e: java.lang.NumberFormatException){}
        }
        intent = Intent().apply {
            val result = "${name.text.toString()}*_*${surname.text.toString()}*_*${age.text.toString()}"
            putExtra("result", result)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}