package otus.gpb.homework.activities.receiver

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        //
        showPayload(this)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showPayload(activity: ReceiverActivity) {
        // Полученные из Intent extras  #1
        val title:       String = this.intent.extras?.getString("title").toString()
        val description: String = this.intent.extras?.getString("description").toString()
        val year:        String = this.intent.extras?.getString("year").toString()
        // В зависимости от названия фильма отобразите картинку которая лежит в ресурсах(res/drawable) в posterImageView
        val image : Drawable? =
            when (title) {
                "Интерстеллар" -> activity.getDrawable(R.drawable.interstellar)
                "Славные парни" -> activity.getDrawable(R.drawable.niceguys)
                else -> {
                    return
                }
            }

        // #1 отобразите в соответсвующих полях:
        // title → titleTextView
        // year → yearTextView
        // description → descriptionTextView
        activity.findViewById<TextView>(R.id.titleTextView).text = title
        activity.findViewById<TextView>(R.id.yearTextView).text = year
        activity.findViewById<TextView>(R.id.descriptionTextView).text = description
        activity.findViewById<ImageView>(R.id.posterImageView).setImageDrawable(image)

    }
}
