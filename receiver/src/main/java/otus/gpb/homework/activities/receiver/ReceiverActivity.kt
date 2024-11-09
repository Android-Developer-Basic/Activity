package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        if (intent?.extras != null) {
            val title = intent.extras!!.getString(KEY_TITLE)
            findViewById<TextView>(R.id.titleTextView).text = title
            findViewById<TextView>(R.id.descriptionTextView).text = intent.extras!!.getString(
                KEY_DESC)
            findViewById<TextView>(R.id.yearTextView).text = intent.extras!!.getString(KEY_YEAR)
            if (title == "Интерстеллар") {
                val image =  AppCompatResources.getDrawable(this, R.drawable.interstellar)
                findViewById<ImageView>(R.id.posterImageView).setImageDrawable(image)
            }
        }
    }

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_DESC = "desc"
        const val KEY_YEAR = "year"
    }
}
