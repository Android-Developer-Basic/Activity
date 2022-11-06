package otus.gpb.homework.activities.sender

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.gpb.homework.activities.sender.databinding.ActivitySenderBinding

class SenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySenderBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }


    }
}
