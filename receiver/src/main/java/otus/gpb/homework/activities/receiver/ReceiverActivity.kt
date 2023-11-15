package otus.gpb.homework.activities.receiver

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ReceiverActivity : AppCompatActivity(R.layout.activity_receiver) {
    private val TAG = "MovieReceiver"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        intent.extras?.let {
            findViewById<TextView>(R.id.titleTextView).text = it.getString("title")
            findViewById<TextView>(R.id.descriptionTextView).text = it.getString("description")
            findViewById<TextView>(R.id.yearTextView).text = it.getString("year")
            displayImage(it.getString("image"))
        }
    }
    private fun displayImage(imgName: String?) {
        val img=findViewById<ImageView>(R.id.posterImageView)
        if (imgName.isNullOrEmpty()) {
            img.visibility=ImageView.INVISIBLE
            return
        } else {
            val res = getImageRes(imgName)
            img.setImageDrawable(res)
            img.visibility=ImageView.VISIBLE
        }
    }

    private fun getImageRes(img: String): Drawable? {
        val context=applicationContext
        val res = context?.let {
            ContextCompat.getDrawable(
                it,
                resources.getIdentifier(img, "drawable",context.getPackageName())
            )
        }
        return res
    }

}