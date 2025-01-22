package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

private const val makePhoto = "Сделать фото"
private const val choosePhoto = "Выбрать фото"
private const val chooseAction = "Выберите действие"

const val FILL_FORM_ACTIVITY_CODE = 123

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var textViewName: TextView
    private lateinit var textViewSurname: TextView
    private lateinit var textViewAge: TextView
    private lateinit var imageUri: Uri


    private val getPictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri == null)
            Toast.makeText(this, "Картинка не выбрана", Toast.LENGTH_SHORT).show()
        else {
            imageUri = uri!!
            populateImage(uri!!)
        }
    }

    private val fillFormActivityLauncher = registerForActivityResult(
        ContractFillFormActivity()
    ) { result ->
        if (result != null) {
            textViewName.text = result.name
            textViewSurname.text = result.surname
            textViewAge.text = result.age
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        button = findViewById(R.id.button4)
        textViewName = findViewById(R.id.textview_name)
        textViewSurname = findViewById(R.id.textview_surname)
        textViewAge = findViewById(R.id.textview_age)


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

        imageView.setOnClickListener(listener)
        button.setOnClickListener {
            //startActivityForResult(Intent(this, FillFormActivity::class.java), FILL_FORM_ACTIVITY_CODE)      // deprecated !
            val profile = Profile(
                name = textViewName.text.toString(),
                surname = textViewSurname.text.toString(),
                age = textViewAge.text.toString()
            )
            fillFormActivityLauncher.launch(profile)
        }
    }

    // старый метод, не используется
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            FILL_FORM_ACTIVITY_CODE -> {
                if (resultCode == RESULT_OK && data != null) {
                    textViewName.text = data.getStringExtra("result_name") ?: ""
                    textViewSurname.text = data.getStringExtra("result_surname") ?: ""
                    textViewAge.text = data.getStringExtra("result_age") ?: ""
                }
            }
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {

        val intent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            setPackage("org.telegram.messenger")
            type = "image/jpeg"
            putExtra(Intent.EXTRA_TEXT, "Name: ${textViewName.text} \n" +
                    "Surname: ${textViewSurname.text}\n" +
                    "Age: ${textViewAge.text}")
        }

        try {
            intent.putExtra(Intent.EXTRA_STREAM, imageUri)
        }
        catch (e: UninitializedPropertyAccessException) {
            Toast.makeText(this, "фото не было выбрано!", Toast.LENGTH_SHORT).show()
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Установите Telegram!", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) {
        isGranted ->
            when {
                isGranted -> {
                    // Пользователь выдал разрешение на использование камеры → отобразите в ImageView ресурс R.drawable.cat
                    imageView.setImageDrawable(getDrawable(R.drawable.cat))
                }

                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    //Пользователь еще раз запросил разрешение на использование камеры после отмены → покажите Rationale Dialog, и объясните зачем вам камера
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Очень нужна камера")
                    builder.setPositiveButton("Дать доступ") { _, _ ->
                        // По клику на кнопку “Дать доступ” повторно запросите разрешение.
                        // Пользователь повторно запретил использовать камеру → Покажите диалоговое окно с одной кнопкой → “Открыть настройки”.
                        // По клику на кнопку отправьте пользователя в настройки приложения, с возможностью поменять разрешение

                        startActivity(Intent().apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = Uri.fromParts("package", packageName, null)
                        })
                    }

                    builder.setNegativeButton("Отмена") { _, _ ->
                        //по клику на кнопку “Отмена” закройте диалоговое окно
                    }

                    builder.create().show()
                }
                else -> {
                    // Пользователь не разрешил использовать камеру первый раз → ничего не делаем
                }
            }
        }

    private val listener = View.OnClickListener { view ->

        when (view.id) {

            R.id.imageview_photo -> {

                val arrStr = arrayOf(makePhoto, choosePhoto)
                var chosenItem: Int = 0
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Выберите действие")
                builder.setSingleChoiceItems(arrStr, 0) { dialog, which ->
                    chosenItem = which
                }

                builder.setPositiveButton("Далее") { dialog, which ->

                    when {
                        arrStr[chosenItem] == makePhoto -> {
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                            {
                                imageView.setImageDrawable(getDrawable(R.drawable.cat))
                            }
                            else
                            {
                                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }

                        arrStr[chosenItem] == choosePhoto -> {
                            // По клику на кнопку “Выбрать фото” откройте экран выбора фото из галлереи,
                            // после того как вы получите URI фотографии в ActivityResultCallback вызовите метод populateImage,
                            // чтобы отобразить полученную фотографию в ImageView
                            getPictureUri.launch("image/*")

                        }
                    }
                }

                ActivityResultContracts.StartActivityForResult
                builder.setNegativeButton("Отмена") { _, _ ->
                    // просто выходим
                }

                builder.create().show()
            }
        }
    }
}