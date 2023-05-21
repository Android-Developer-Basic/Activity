package otus.gpb.homework.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    //По клику на кнопку “Выбрать фото” откройте экран выбора фото из галлереи, после того как вы получите URI фотографии в `ActivityResultCallback` вызовите метод `populateImage`, чтобы отобразить полученную фотографию в ImageView
    private val resultImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        val uri = result ?: Uri.EMPTY
        populateImage(uri)
    }
    private val  permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        when {
            //1. Пользователя выдал разрешение на использование камеры → отобразите в ImageView ресурс `R.drawable.cat`
            granted -> {
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat)
                imageView.setImageBitmap(bitmap)
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> openPermissionSetting()
            //2. Пользователь не разрешил использовать камеру первый раз → ничего не делаем
            else -> Toast.makeText(this,R.string.camera_permission_denied, Toast.LENGTH_SHORT).show()
        }

    }

    private val updateProfileInformation = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result ?: return@registerForActivityResult
        if (result.resultCode == Activity.RESULT_OK){
            result.data?.extras?.let {bundle ->
                findViewById<TextView>(R.id.textview_name).text = bundle.getString("PROFILE_NAME").toString()
                findViewById<TextView>(R.id.textview_surname).text = bundle.getString("PROFILE_SURNAME").toString()
                findViewById<TextView>(R.id.textview_age).text = bundle.getString("PROFILE_AGE").toString()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {it ->
            var selectedIndex: Int = -1
            val contextDialog = MaterialAlertDialogBuilder(it.context)
                .setTitle(resources.getString(R.string.source_title))
                .setSingleChoiceItems(arrayOf(resources.getString(R.string.source_camera),resources.getString(R.string.source_galery)),selectedIndex) { dialog, which ->
                    (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                    selectedIndex = which
                }
                .setNegativeButton(resources.getString(R.string.source_cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.source_accept)) { _, _ ->
                    when(selectedIndex){
                        0 -> showRuntimePermissionCamera()
                        1 -> resultImageContent.launch("image/*")
                        else -> Toast.makeText(this,"Warning", Toast.LENGTH_SHORT).show()
                    }
                }
                .create()
            contextDialog.show()
            contextDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
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
            updateProfileInformation.launch(Intent(this, FillFormActivity::class.java))
        }
    }
    private fun showRuntimePermissionCamera() {
        // 3. Пользователь еще раз запросил разрешение на использование камеры после отмены → покажите Rationale Dialog, и объясните зачем вам камера
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                MaterialAlertDialogBuilder(this)
                    .setIcon(android.R.drawable.ic_menu_camera)
                    .setMessage(R.string.camera_dialog_title)
                    .setNegativeButton(R.string.camera_cancel) { dialog, _ ->
                        Toast.makeText(
                            this,
                            getString(R.string.camera_permission_denied),
                            Toast.LENGTH_LONG
                        ).show()
                        dialog.cancel()
                    }
                    .setPositiveButton(R.string.camera_accept_permission) { _, _ ->
                        permissionCamera.launch(Manifest.permission.CAMERA)
                    }
                    .show()
            }
        }
        else permissionCamera.launch(Manifest.permission.CAMERA)
    }
    private fun openPermissionSetting() {
        //4. Пользователь повторно запретил использовать камеру → Покажите диалоговое окно с одной кнопкой → “Открыть настройки”. По клику на кнопку отправьте пользователя в настройки приложения, с возможностью поменять разрешение
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.open_setting_dialog_title)
            .setPositiveButton(R.string.open_setting_dialog_button) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package", packageName, null)
                    data = uri
                }
                startActivity(intent)
            }
            .show()
    }
    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun getProfile(): Pair<String, Bitmap> {
        val name = findViewById<TextView>(R.id.textview_name).text
        val surname = findViewById<TextView>(R.id.textview_surname).text
        val age = findViewById<TextView>(R.id.textview_age).text
        val text = "Name: $name, Surname: $surname, Age: $age"
        val imageView = findViewById<ImageView>(R.id.imageview_photo)
        val image = imageView.drawable.toBitmap()
        return Pair(text, image)
    }

    private fun openSenderApp() {
        val profile = getProfile()
        val sendToTelegramIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, profile.first)
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, profile.second.rowBytes)
        }
        sendToTelegramIntent.setPackage("org.telegram.messenger")
        sendToTelegramIntent.resolveActivity(packageManager)?.let {
             startActivity(sendToTelegramIntent)
        }
    }
}