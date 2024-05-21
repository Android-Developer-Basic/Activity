package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mylibrary.Payload

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        val p: Payload? = intent.extras?.getParcelable<Payload>("movie")

        findViewById<TextView>(R.id.titleTextView).setText(p?.title)
        findViewById<TextView>(R.id.yearTextView).setText(p?.year)
        findViewById<TextView>(R.id.descriptionTextView).setText(p?.description)

        imageByName(p?.title)
    }

   fun imageByName(title:String?) {
       var id: Int? = null
       when (title) {
           "Интерстеллар" -> id = R.drawable.interstellar
           "Славные парни" -> id = R.drawable.niceguys
       }
       if (id != null) {
           val drawable = ContextCompat.getDrawable(this, id)
           findViewById<ImageView>(R.id.posterImageView).setImageDrawable(drawable)
       }
   }
}