package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity(R.layout.activity_receiver) {

    private val posterImageView by lazy {
        findViewById<ImageView>(R.id.posterImageView)
    }

    private val titleTextView by lazy {
        findViewById<TextView>(R.id.titleTextView)
    }

    private val descriptionTextView by lazy {
        findViewById<TextView>(R.id.descriptionTextView)
    }

    private val yearTextView by lazy {
        findViewById<TextView>(R.id.yearTextView)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val bundle = intent?.getBundleExtra("payLoad")
        val title = bundle?.getString("title")

        if (title.isNullOrEmpty()) return

        titleTextView.text = title
        bundle.getString("year").also {
            yearTextView.text = it
        }
        bundle.getString("description").also {
            descriptionTextView.text = it
        }

        when(title){
            "Интерстеллар" -> posterImageView.setImageDrawable(AppCompatResources.getDrawable(this@ReceiverActivity, R.drawable.interstellar))
            "Славные парни" -> posterImageView.setImageDrawable(AppCompatResources.getDrawable(this@ReceiverActivity, R.drawable.niceguys))
        }
    }
}