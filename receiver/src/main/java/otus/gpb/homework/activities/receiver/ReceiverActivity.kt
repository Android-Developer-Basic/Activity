package otus.gpb.homework.activities.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    private var titleTextView: TextView? = null
    private var descriptionTextView: TextView? = null
    private var yearTextView: TextView? = null
    private var posterImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        if (intent != null) {

            // Получаем данные из интента
            val title = intent.getStringExtra("title")
            val year = intent.getStringExtra("year")
            val description = intent.getStringExtra("description")

            titleTextView = findViewById(R.id.titleTextView)
            descriptionTextView = findViewById(R.id.descriptionTextView)
            yearTextView = findViewById(R.id.yearTextView)
            posterImageView = findViewById(R.id.posterImageView)

            titleTextView?.text = title
            descriptionTextView?.text = description
            yearTextView?.text = year
            val drawableResId = when (title) {
                "Интерстеллар" -> R.drawable.interstellar
                "Славные парни" -> R.drawable.niceguys
                else -> R.drawable.interstellar // Ресурс картинки по умолчанию
            }

            // Получаем Drawable из ресурса
            val drawable = getDrawable(drawableResId)

            // Устанавливаем полученную картинку в ImageView
            posterImageView?.setImageDrawable(drawable)
        }
    }
}


