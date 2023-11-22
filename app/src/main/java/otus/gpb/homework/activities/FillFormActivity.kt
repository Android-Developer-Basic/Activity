package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import android.util.Log
import com.google.android.material.datepicker.CalendarConstraints
import java.util.Calendar

class FillFormActivity : AppCompatActivity(R.layout.activity_fill_form) {
    private var userData=UserDataSet()
    private val TAG = "FillFormActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.button_cancel).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        findViewById<Button>(R.id.buttonDatePicker).setOnClickListener {
            val calendar = Calendar.getInstance()
            val to=calendar.timeInMillis
            calendar.set(1971, 1, 1)
            val from=calendar.timeInMillis
            val constraint = CalendarConstraints.Builder()
                .setStart(from)
                .setEnd(to)
                .build()
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(constraint)
                    .build().apply {
                        addOnPositiveButtonClickListener() {
                            userData.birthdate=Date(it).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            showDate()
                        }
                    }
            datePicker.show(supportFragmentManager, "DatePicker")
        }

        findViewById<Button>(R.id.button_save)?.setOnClickListener {
            updateData()
            val intent = Intent().apply {
                Log.d(TAG,"Sended data <$tagUserInfo>: {${userData.toString()}")
                putExtra(tagUserInfo,userData)
            }
            setResult(RESULT_OK,intent)
            finish()

        }
    }

    override fun onStart() {
        super.onStart()
        fillForm()
    }

    private fun showDate() {
        if (userData.birthdate.year!=1900) {
            val date = userData.birthdate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            findViewById<TextView>(R.id.dateText).setText(date)
        }
    }

    private fun fillForm() {
        val intent = getIntent();
        intent.getParcelableExtra<UserDataSet>(tagUserInfo)?.let {
            userData=it.copy()
        }
        findViewById<EditText>(R.id.fillFormFirstName).apply {
            setText(userData.firstname,TextView.BufferType.EDITABLE)
        }
        findViewById<EditText>(R.id.fillFormLastName).apply {
            setText(userData.lastname,TextView.BufferType.EDITABLE)
        }
        showDate()
    }
    private fun updateData() {
        findViewById<EditText>(R.id.fillFormFirstName).let {
            userData.firstname=it.getText().toString()
        }
        findViewById<EditText>(R.id.fillFormLastName).let {
            userData.lastname=it.getText().toString()
        }
        showDate()
    }
}
