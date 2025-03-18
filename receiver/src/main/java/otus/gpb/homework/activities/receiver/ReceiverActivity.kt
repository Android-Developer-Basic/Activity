package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val title = intent?.getStringExtra("title")
        val year = intent?.getStringExtra("year")
        val description = intent?.getStringExtra("description")

        findViewById<TextView>(R.id.titleTextView).text = title
        findViewById<TextView>(R.id.yearTextView).text = year
        findViewById<TextView>(R.id.descriptionTextView).text = description

        val image = findViewById<ImageView>(R.id.posterImageView)
        if (title == "Интерстеллар")
            image.setImageDrawable(getDrawable(R.drawable.interstellar))
        else if (title == "Славные парни")
            image.setImageDrawable(getDrawable(R.drawable.niceguys))
    }

}
