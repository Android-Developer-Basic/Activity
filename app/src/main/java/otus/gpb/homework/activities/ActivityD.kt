package otus.gpb.homework.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.databinding.ActivityDBinding
class ActivityD : AppCompatActivity() {

    private lateinit var binding: ActivityDBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.setBackgroundColor(Color.parseColor("#ffeb3b"))

        Log.d("ActivityD", "onCreate")
    }
}