package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat

const val RESULT_KEY = "result_key"

open class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private var imageUri: Uri? = null

    private var profile: Profile? = null

    private var isAccessRequested = false

    private val permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageResource(R.drawable.cat)
                imageUri = null
            }

            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package", packageName, null)
                    data = uri
                }
                startActivity(intent)
            }
            else -> {
                isAccessRequested = true
            }
        }

    }

    private val storage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            populateImage(it)
            imageUri = it
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val resultCode = result.resultCode

        if (resultCode == RESULT_OK && data != null) {
            profile = data.getParcelableExtra(RESULT_KEY)
            findViewById<TextView>(R.id.textview_name).text = profile?.name
            findViewById<TextView>(R.id.textview_surname).text = profile?.surname
            findViewById<TextView>(R.id.textview_age).text = profile?.age
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)

        imageView.setOnClickListener {
               alertDialog()
        }


        val button = findViewById<Button>(R.id.button4)
        button?.setOnClickListener {
            val intent = Intent(this, FillFormActivity::class.java)
            launcher.launch(intent)
        }

        findViewById<TextView>(R.id.textview_name).setOnClickListener {
            val resultName = findViewById<EditText>(R.id.textview_name).text.toString()
            val intent = Intent().putExtra(RESULT_KEY, resultName)
            setResult(RESULT_OK, intent)
            finish()
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
    private fun camera() {
        val isGrantedCamera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (!isGrantedCamera) {
            if(isAccessRequested) permissionDialog()
            else permissionCamera.launch(Manifest.permission.CAMERA)
        }
    }
     private fun imageFromGallery(){
         storage.launch("image/*")
     }
    private fun alertDialog() {
        val listitem = arrayOf("Сделать фото", "Выбрать фото")
        val builder = AlertDialog.Builder(this)
        var selected: Int? = null
        builder.setTitle("Выберите действие").apply {
            setSingleChoiceItems(listitem, -1) { _, item ->
                selected = item
            }
            setPositiveButton("Ок") { _, _ ->
                when (selected) {
                    0 -> camera()
                    1 -> imageFromGallery()
                }
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        }
        builder.create().show()
    }

    private fun permissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Доступ к камере")
            setPositiveButton("Дать доступ") { _, _ ->
                permissionCamera.launch(Manifest.permission.CAMERA)
            }
            setNegativeButton("Отмена") { dialog, _ ->
                settingsDialog()
                dialog.cancel()
            }
        }
        builder.create().show()
    }

    private fun settingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Важное сообщение")
            setPositiveButton("Открыть настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + context.packageName)
                startActivity(intent)
            }
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
        if (profile != null) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                setType("image/*")
                putExtra(Intent.EXTRA_TEXT,
                    "name=${profile?.name}\n&surname=${profile?.surname}\n&age=${profile?.age}")
                if (imageUri != null) putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            startActivity(intent)
        }
    }
}