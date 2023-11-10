package otus.gpb.homework.activities

import android.Manifest
import android.content.Context
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class EditProfileActivity : AppCompatActivity() {

    private val getContentContract = registerForActivityResult(ActivityResultContracts.GetContent()) {result ->
        if (result != null) {
            populateImage(result)
        }
    }

    private var pressPhoto = 0
    private val permissionCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()){granted ->
        when {
            granted -> {
                findViewById<ImageView>(R.id.imageview_photo).setImageDrawable(getDrawable(R.drawable.cat))
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) ->{
                createSettingsDialog(this)
            }
            else -> {
            }
        }
    }

    private lateinit var imageView: ImageView
    private lateinit var textName: TextView
    private lateinit var textSurname: TextView
    private lateinit var textAge: TextView
    private lateinit var buttonEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            createDualDialog(this)
        }
        textName = findViewById<EditText>(R.id.textview_name)
        textSurname = findViewById(R.id.textview_surname)
        textAge = findViewById(R.id.button4)

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
        buttonEdit = findViewById(R.id.button4)
    }

    private fun createAccessDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder
            .setNegativeButton("Дать доступ") { _, _ ->
                permissionCamera.launch(Manifest.permission.CAMERA)
            }
            .setPositiveButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
        builder.create().show()
    }

    private fun createSettingsDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder
            .setNegativeButton("Открыть настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package",packageName,null)
                    data = uri
                }
                startActivity(intent)
            }
        builder.create().show()
    }
    private fun createDualDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Выберите")
            .setCancelable(true)
            .setNegativeButton("Сделать фото") { _, _ ->
                if (pressPhoto ==0) {
                    permissionCamera.launch(Manifest.permission.CAMERA)
                }
                else {
                    createAccessDialog(this)
                }
                pressPhoto++
            }
            .setPositiveButton("Выбрать фото") { dialog, id ->
                getContentContract.launch("image/*")
            }
        builder.create().show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}

