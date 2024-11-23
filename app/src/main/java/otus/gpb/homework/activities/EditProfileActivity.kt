package otus.gpb.homework.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var actionDialog: AlertDialog? = null
    private val REQUEST_CAMERA_PERMISSION = 111

    private val permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                // получили доступ
                insertCatPhoto()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Требуется доступ к камере")
                    setMessage("Чтобы сделать фото, нужен доступ к камере устройства")
                    setPositiveButton("Дать доступ") { actionDialog, _ ->
                        ActivityCompat.requestPermissions(this@EditProfileActivity, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
                        actionDialog.cancel()
                    }
                    setNegativeButton("Отмена") { actionDialog, _ ->
                        actionDialog.cancel()
                    }
                    show()
                }
            }
            !shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Камера все же потребуется")
                    setMessage("Без камеры никак не обойтись")
                    setPositiveButton("Открыть настройки") { actionDialog, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        }
                        startActivity(intent)
                        actionDialog.cancel()
                    }
                    show()
                }
            }
        }
    }

    private val takePictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            populateImage(uri)
        }
    }

    private val editProfileLauncher = registerForActivityResult(
        FillFormContract()
    ) { result ->
        findViewById<TextView>(R.id.textview_name).text = result?.name
        findViewById<TextView>(R.id.textview_surname).text = result?.surname
        findViewById<TextView>(R.id.textview_age).text = result?.age
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        createActionDialog()

        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener{
            actionDialog?.show()
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
            editProfileLauncher.launch("edit")
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun getImageUri():Uri? {
        val bitmap: Bitmap = findViewById<ImageView>(R.id.imageview_photo).drawable.toBitmap()
        val imagesFolder = File(cacheDir, "images")
        var contentUri: Uri? = null
        try {
            imagesFolder.mkdirs() //create if not exists
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)
            stream.flush()
            stream.close()
            contentUri = FileProvider.getUriForFile(this, "com.technifysoft.shareimagetext.fileprovider", file)
        } catch (_: Exception) {
        }
        return contentUri
    }

    private fun openSenderApp() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            setPackage("org.telegram.messenger")
            val name = findViewById<TextView>(R.id.textview_name).text
            val surname = findViewById<TextView>(R.id.textview_surname).text
            val age = findViewById<TextView>(R.id.textview_age).text
            putExtra(Intent.EXTRA_TEXT, "$name $surname, возраст $age")
            putExtra(Intent.EXTRA_STREAM, getImageUri())
        }
        startActivity(intent)
    }

    private fun createActionDialog() {
        val variants = arrayOf("Сделать фото", "Выбрать фото")
        val dialogBuilder = AlertDialog.Builder(this).apply {
            setTitle("Выберите действие")
            setItems(variants) { _, which ->
                when (which) {
                    0 -> makePhoto()
                    1 -> pickPhoto()
                }
            }
        }
        actionDialog = dialogBuilder.create()
    }

    private fun insertCatPhoto() {
        imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cat))
    }

    private fun makePhoto() {
        val isGrantedCamera = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;

        if (!isGrantedCamera) {
            permissionCamera.launch(android.Manifest.permission.CAMERA)
        } else {
            insertCatPhoto()
        }
    }

    private fun pickPhoto() {
        takePictureUri.launch("image/*")
    }
}