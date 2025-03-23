package otus.gpb.homework.activities.receiver

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiverActivity : AppCompatActivity() {

    lateinit var titleTV: TextView
    lateinit var yearTV: TextView
    lateinit var descriptionTV: TextView
    lateinit var posterImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        titleTV = findViewById(R.id.titleTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        yearTV = findViewById(R.id.yearTV)
        posterImageView = findViewById(R.id.posterImageView)


        val title = intent.extras?.getString("title")?:"noname"
        val year = intent.extras?.getString("year")?:"0000"
        val description = intent.extras?.getString("description")?:"empty description"


        val images = mutableMapOf<String, Int>()
        val drawableClass = R.drawable::class.java
        val imageID  = drawableClass.declaredFields
            .filter { it.name.toString().lowercase() == title.toString().lowercase() }
            .map { it.getInt(null) }


        titleTV.text = title
        yearTV.text = year
        descriptionTV.text = description
        posterImageView.setImageResource(imageID.firstOrNull() ?: R.drawable.noname)

    }


}
