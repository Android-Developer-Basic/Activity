package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val titleKey = "titleKey"
const val titleyear = "titleyear"
const val titledescription = "titledescription"

class ReceiverActivity : AppCompatActivity() {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title= intent.extras?.getString(titleKey)
        findViewById<TextView>(R.id.titleTextView).text=title

        val titleyear= intent.extras?.getString(titleyear)
        findViewById<TextView>(R.id.yearTextView).text=titleyear

        val titledescription= intent.extras?.getString(titledescription)
        findViewById<TextView>(R.id.descriptionTextView).text=titledescription

        findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable (R.drawable.interstellar))
    }
}
