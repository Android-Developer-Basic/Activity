package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.databinding.ActivityCBinding

class ActivityC : AppCompatActivity() {

    private lateinit var binding: ActivityCBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openActivityAButton.setOnClickListener {
            openActivityA()
        }
        binding.openActivityDButton.setOnClickListener {
            openActivityD()
        }
        binding.closeActivityCButton.setOnClickListener {
            closeActivityC()
        }
        binding.closeStackButton.setOnClickListener {
            closeStack()
        }
    }

    private fun openActivityA() {
        val intent = Intent(this, ActivityA::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    private fun openActivityD() {
        val intent = Intent(this, ActivityD::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    private fun closeActivityC() {
        finish()
    }

    private fun closeStack() {
        val intent = Intent(this, ActivityA::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
        finishAffinity()
    }
}