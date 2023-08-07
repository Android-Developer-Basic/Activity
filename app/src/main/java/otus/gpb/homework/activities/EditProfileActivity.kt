package otus.gpb.homework.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditProfileActivity : AppCompatActivity() {
    private var manInfo = DEFAULT_MAN_INFO

    private var uriImage: Uri? = null

    private val textViewName by lazy {
        findViewById<TextView>(R.id.textview_name)
    }
    private val textViewSurName by lazy {
        findViewById<TextView>(R.id.textview_surname)
    }
    private val textViewAge by lazy {
        findViewById<TextView>(R.id.textview_age)
    }
    private val button4 by lazy {
        findViewById<Button>(R.id.button4)
    }
    private val contract = EditProfileFillFromContract()
    private val resultContract = registerForActivityResult(contract) { result ->
        if (result == null || result == DEFAULT_MAN_INFO) {
            toastShow("Ничего не было передано")
        } else {
            manInfo = result
            textViewName.text = result.firstName
            textViewSurName.text = result.lastName
            textViewAge.text = result.age.toString()
        }
    }

    private val userSelect = arrayOf("Сделать фото", "Выбрать фото")

    private val imageView by lazy {
        findViewById<ImageView>(R.id.imageview_photo)
    }
    private var dialog: AlertDialog? = null

    private val gallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            populateImage(it)
        }
    }
    private val camera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            imageView.setImageBitmap(it)

        }

    private val runtimePermissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            when {
                it -> {
                    camera.launch(null)
                }

                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        val uri = Uri.fromParts("package", packageName, null)
                        data = uri
                    }
                    startActivity(intent)
                }

                else -> {
                    toastShow("Разрешение не было получено")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        button4.setOnClickListener {
            resultContract.launch(manInfo)
        }
        dialog = AlertDialog.Builder(this)
            .setView(view())
            .create()
        imageView.setOnClickListener {
            dialog?.show()
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
    }

    private fun view(): View? {
        val customLayout = layoutInflater.inflate(R.layout.custom_dialog_layout, null)
        val listView = customLayout.findViewById<ListView>(R.id.list)
        listView.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice, userSelect
        )
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        val positiveButton = customLayout.findViewById<Button>(R.id.positive_button)
        positiveButton.setOnClickListener {
            // Действие при нажатии на кнопку "Подтвердить"
            val selectedItem = userSelect[listView.checkedItemPosition]
            //Toast.makeText(this, "Выбран элемент: $selectedItem", Toast.LENGTH_SHORT).show()
            if (selectedItem == userSelect[0]) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    AlertDialog.Builder(this)
                        .setTitle("Объяснение")
                        .setMessage("Очень нужно")
                        .setPositiveButton("Дать доступ") { _, _ ->
                            runtimePermissionCamera.launch(Manifest.permission.CAMERA)
                        }
                        .setNegativeButton("Отмена") { alertDialog, _ ->
                            alertDialog.cancel()
                        }
                        .create()
                        .show()
                } else {
                    runtimePermissionCamera.launch(Manifest.permission.CAMERA)
                }
                //runtimePermissionCamera.launch(Manifest.permission.CAMERA)
            } else {
                gallery.launch("image/*")
                //Toast.makeText(this, "Выбран элемент: $selectedItem", Toast.LENGTH_SHORT).show()
            }
            dialog?.dismiss()
        }

        val negativeButton = customLayout.findViewById<Button>(R.id.negative_button)
        negativeButton.setOnClickListener {
            // Действие при нажатии на кнопку "Отменить"
            dialog?.dismiss()
        }
        return customLayout
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        if (manInfo != DEFAULT_MAN_INFO) {
            val packageName = "org.telegram.messenger"
            val telegramIntent = Intent(Intent.ACTION_SEND)
            telegramIntent.apply {
                setPackage(packageName)
                putExtra(Intent.EXTRA_TEXT, manInfo.toString())
                if(uriImage!=null){
                    type = "image/*"
                    putExtra(Intent.EXTRA_STREAM, uriImage)
                }
            }
            startActivity(telegramIntent)
            // Проверяем, есть ли приложение Telegram установлено на устройстве -- если добавить эту проверку , то вообще не открывается телеграмм
            /*if (telegramIntent.resolveActivity(packageManager) != null) {
                startActivity(telegramIntent)
            } else {
                toastShow("На устройстве не установлен Telegram")
            }*/
        } else {
            toastShow("загрузите даные профиля")
        }
    }

    private fun toastShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        val DEFAULT_MAN_INFO = ManInfo("", "", -1)
    }
}