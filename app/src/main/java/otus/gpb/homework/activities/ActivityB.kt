package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.databinding.ActivityBBinding

class ActivityB : AppCompatActivity() {

    private val binding by lazy {
        ActivityBBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonOpenActivityC.setOnClickListener(){
            val intent = ActivityC.newIntent(this)
            startActivity(intent)
        }

    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, ActivityB::class.java)
        }
    }
}