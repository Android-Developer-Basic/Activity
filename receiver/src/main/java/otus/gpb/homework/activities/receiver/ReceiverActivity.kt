package otus.gpb.homework.activities.receiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val posterImageView = findViewById<ImageView>(R.id.posterImageView)

        // Достаём extras из Intent
        val title = intent.getStringExtra("title") ?: ""
        val year = intent.getStringExtra("year") ?: ""
        val description = intent.getStringExtra("description") ?: ""

        // Заполняем текстовые поля
        titleTextView.text = title
        yearTextView.text = year
        descriptionTextView.text = description

//            - В зависимости от названия фильма отобразите картинку которая лежит в ресурсах(*res/drawable*) в `posterImageView`
        val posterResId = when (title) {
            "Славные парни" -> R.drawable.niceguys
            "Интерстеллар" -> R.drawable.interstellar
            else -> android.R.drawable.ic_menu_gallery // заглушка
        }
//        можно так, работает на всех версиях
//        posterImageView.setImageResource(posterResId)
//        делаем так по условию (с импортом Drawable):
        val drawable = AppCompatResources.getDrawable(this, posterResId)
        // Передаём Drawable в ImageView
        drawable?.let { posterImageView.setImageDrawable(it) }

    }
}
