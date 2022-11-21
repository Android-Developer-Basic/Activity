package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        intent.extras?.getString("title")
        intent.extras?.getString("year")
        intent.extras?.getString("description")



    }
}