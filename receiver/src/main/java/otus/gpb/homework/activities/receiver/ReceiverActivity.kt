package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val KEY_TITLE = "title"
const val KEY_YEAR = "year"
const val KEY_DESCR = "descr"

const val TITLE_INTERSTELLAR = "Интерстеллар"
const val TITLE_NICEGUYS = "Славные парни"

class ReceiverActivity : AppCompatActivity() {

    private var txwTitle : TextView? = null
    private var txwYear : TextView? = null
    private var txwDescription : TextView? = null
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        txwTitle = findViewById(R.id.titleTextView)
        txwYear = findViewById(R.id.yearTextView)
        txwDescription = findViewById(R.id.descriptionTextView)
        imageView = findViewById(R.id.posterImageView)

        val keyTitle = intent.extras?.getString(KEY_TITLE)
        val keyYear = intent.extras?.getString(KEY_YEAR)
        val keyDescr = intent.extras?.getString(KEY_DESCR)

        if (keyTitle == TITLE_INTERSTELLAR) {
            imageView?.setImageDrawable(getDrawable(R.drawable.interstellar))

        }
        else if (keyTitle == TITLE_NICEGUYS) {
            imageView?.setImageDrawable(getDrawable(R.drawable.niceguys))

        }

        txwTitle!!.text = keyTitle
        txwYear!!.text = keyYear
        txwDescription!!.text = keyDescr
    }
}
