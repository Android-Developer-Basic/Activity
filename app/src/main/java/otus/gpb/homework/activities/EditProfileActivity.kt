package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


open class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    var image: Uri? = null

    val resultImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) {result ->
           populateImage(result)
        }


    private val resultContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                findViewById<ImageView>(R.id.imageview_photo). apply {
                    setImageDrawable(getDrawable(R.drawable.cat))
                }

            } else {
                val dontShowAgain =
                    !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                if (dontShowAgain) {

                    MaterialAlertDialogBuilder(this)
                        .setPositiveButton("Открыть настройки") { dialog, _ ->
                            val intent = Intent (Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                val uri = Uri.fromParts("package", packageName, null)
                                data = uri
                            }
                            startActivity(intent)
                        }
                        .show()
                }
             }
       }

    val resultProfile = registerForActivityResult(Contract()) {result ->
          findViewById<TextView>(R.id.textview_name).apply {
               text = result?.name
          }
          findViewById<TextView>(R.id.textview_surname).apply {

              text = result?.surname
          }
          findViewById<TextView>(R.id.textview_age).apply {

              text = result?.age
          }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {

            val items = arrayOf("Сделать фото", "Выбрать фото")

            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.title))
                .setItems(items) { dialog, which ->
                    val selected = items[which]
                    if (selected == "Сделать фото") {

                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            MaterialAlertDialogBuilder(this)
                            .setTitle("Необходимо разрешение!")
                            .setMessage("Для использования камеры необходимо Ваше разрешение")
                            .setPositiveButton("Дать доступ") { dialog, _ ->
                            resultContract.launch(Manifest.permission.CAMERA)
                            }
                            .setNegativeButton("Отмена") { dialog, _ ->
                            dialog.cancel()
                            }
                        .show()
                        } else {
                            resultContract.launch(Manifest.permission.CAMERA)
                        }

                    } else {
                        resultImage.launch("image/*")
                    }

                }
                .show()
        }

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
             resultProfile.launch("")
        }

    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri?) {
        val bitmap = BitmapFactory.decodeStream(uri?.let { contentResolver.openInputStream(it) })
        image = uri
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val inputName = findViewById<TextView>(R.id.textview_name)
        val inputSurname = findViewById<TextView>(R.id.textview_surname)
        val inputAge = findViewById<TextView>(R.id.textview_age)
        val telegramIntent = Intent ().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, User(inputName.text.toString(), inputSurname.text.toString(), inputAge.text.toString()).toString())
            putExtra(Intent.EXTRA_STREAM, image)
            type = "image/*"
            setPackage("org.telegram.messenger")
        }
        startActivity(Intent.createChooser(telegramIntent, "Share data"))
    }
}