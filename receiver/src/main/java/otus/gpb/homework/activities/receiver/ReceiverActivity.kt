package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity() {
    private var titleView: TextView? = null
    private var yearView: TextView? = null
    private var descrView: TextView? = null
    private lateinit var posterView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        titleView = findViewById(R.id.titleTextView)
        yearView = findViewById(R.id.yearTextView)
        descrView = findViewById(R.id.descriptionTextView)
        posterView = findViewById(R.id.posterImageView)

        val title = intent.extras?.getString("title")
        titleView?.text = title
        yearView?.text = intent.extras?.getString("year")
        descrView?.text = intent.extras?.getString("description")

        when(title){
            "Interstellar" -> {
                val drawable = ContextCompat.getDrawable(this, R.drawable.interstellar)
                posterView.setImageDrawable(drawable)
            }
            "Nice guys" -> {
                val drawable = ContextCompat.getDrawable(this, R.drawable.niceguys)
                posterView.setImageDrawable(drawable)
            }
        }
    }
}
