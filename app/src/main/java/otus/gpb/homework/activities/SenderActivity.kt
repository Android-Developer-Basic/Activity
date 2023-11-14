package otus.gpb.homework.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val buttonToGoogleMaps = findViewById<Button>(R.id.buttonToGoogleMaps)
        val buttonSendEmail = findViewById<Button>(R.id.buttonSendEmail)
        val buttonOpenReceiver = findViewById<Button>(R.id.buttonOpenReceiver)

        buttonToGoogleMaps.setOnClickListener {
            // обработчик нажатия для кнопки "To Google Maps"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=restaurants"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        buttonSendEmail.setOnClickListener {
            // обработчик нажатия для кнопки "Send Email"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Тема письма")
            intent.putExtra(Intent.EXTRA_TEXT, "текст")
            startActivity(Intent.createChooser(intent, "Send Email"))
        }

        buttonOpenReceiver.setOnClickListener {
            // обработчик нажатия для кнопки "Open Receiver"
            val harrypotter = Payload(
                title = "Гарри Поттер и философский камень",
                year = "2001",
                description = "Знаменитая школа Хогвартс – не просто учебное заведение, а целая магическая вселенная. Сюда мечтают поступить дети из разных уголков планеты. Поттеру, Гермиону и Рону, которые потом станут верными друзьями, это удалось. Компанию за время учебы ожидают интересные и увлекательные приключения.\n" +
                        "Наступает одиннадцатый день рождения, но повода радоваться у Гарри нет. Он живет в ненавистном ему семействе Дурслей, которые давно оформили над мальчиком опеку. Они постоянно третируют его и считают, что он не достоин жить в их семье. Однажды он получает странное письмо и узнает, что его родители – настоящие волшебники.\n" +
                        "Странности с мальчиком происходили с самого детства. Он понимал язык змей и мог с ними общаться. А чтобы выпустить животное из вольера, ему достаточно было силой мысли отодвинуть стекло.\n" +
                        "Однажды его забирает великан Хагрид и рассказывает подростку, кто в действительности его мать и отец.\n" +
                        "После закупки волшебной палочки и необходимых учебников Гарри с платформы «девять и три четверти» уезжает учиться в Хогвартс. В школе он познает азы колдовства, найдет товарищей, встретится с соперниками. Его жизнь теперь кардинально меняется. С этого момента она будет наполнена невероятными приключениями. Ведь в этом мире возможно все."
            )
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("title", harrypotter.title)
            intent.putExtra("year", harrypotter.year)
            intent.putExtra("description", harrypotter.description)
            startActivity(intent)
        }
    }
}