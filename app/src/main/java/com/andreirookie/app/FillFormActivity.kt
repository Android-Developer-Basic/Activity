package com.andreirookie.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.andreirookie.app.dto.User
import otus.gpb.homework.activities.R

class FillFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_form)

        val nameEditText = findViewById<EditText>(R.id.nameEdit)
        val surnameEditText = findViewById<EditText>(R.id.surnameEdit)
        val ageEditText = findViewById<EditText>(R.id.ageEdit)
        ageEditText.inputType = TYPE_CLASS_NUMBER

        val intentReceived = intent ?: return
        val userReceived = intentReceived.getParcelableExtra<User>(EditProfileActivity.EXTRA_USER)
        userReceived.let {
            nameEditText.setText(userReceived?.name)
            surnameEditText.setText(userReceived?.surname)
            ageEditText.setText(userReceived?.age.toString())
        }

        val applyButton = findViewById<Button>(R.id.applyButton)
        applyButton.setOnClickListener {
            val intentToSend = Intent()

            if (nameEditText.text.isNullOrBlank()
                || surnameEditText.text.isNullOrBlank()
                || ageEditText.text.isNullOrBlank()
            ) {
                setResult(Activity.RESULT_CANCELED, intentToSend)
            } else {
                val user = User(
                    name = nameEditText.text.toString(),
                    surname = surnameEditText.text.toString(),
                    age = ageEditText.text.toString().toInt()
                )
                intentToSend.putExtra(EditProfileActivity.EXTRA_USER, user)
                setResult(Activity.RESULT_OK, intentToSend)
            }
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}