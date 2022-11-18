package otus.gpb.homework.activities.receiver

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
//import dto.MovieDTO

class ReceiverActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
//        val intent = intent.extras?.getParcelable("dataset", MovieDTO::class.java) as MovieDTO
//            findViewById<TextView>(R.id.titleTextView).text = intent.title
//            findViewById<TextView>(R.id.yearTextView).text = intent.year
//            findViewById<TextView>(R.id.descriptionTextView).text = intent.description
//            findViewById<ImageView>(R.id.posterImageView).setImageDrawable(getDrawable(R.drawable.interstellar))
        val intent = intent
        findViewById<TextView>(R.id.titleTextView).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.descriptionTextView).text= intent.getStringExtra("description")
        findViewById<TextView>(R.id.yearTextView).text= intent.getStringExtra("year")

        when (intent.getStringExtra("title")) {
            "Интерстеллар" -> findViewById<ImageView>(R.id.posterImageView).setImageDrawable(this.getDrawable(R.drawable.interstellar))
            "Славные парни" -> findViewById<ImageView>(R.id.posterImageView).setImageDrawable(this.getDrawable(R.drawable.niceguys))
        }
    }
}