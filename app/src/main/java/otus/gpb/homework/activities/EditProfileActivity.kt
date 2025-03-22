package otus.gpb.homework.activities

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat
import java.io.Serializable

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var buttonEdit: Button
    private lateinit var txtName: TextView
    private lateinit var txtSurname: TextView
    private lateinit var txtAge: TextView

    private val launcherEditProfile = registerForActivityResult(
        ContractFillProfile()
    ) { result ->
        txtAge.text = result?.age.toString()
        txtName.text = result?.name.toString()
        txtSurname.text = result?.surname.toString()
    }

    private fun getCurrentName(): String {
        return txtName.text.toString()
    }

    private fun getCurrentSurname(): String {
        return txtSurname.text.toString()
    }

    private fun getCurrentAge(): Int {
        var age = 0
        runCatching { age = txtAge.text.toString().toInt() }
        return age
    }

    private val permissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted ->
            when {
                isGranted -> {
                    takePhoto(false)
                }

                !shouldShowRequestPermissionRationale(permission.CAMERA) -> {
                    showRationaleDialog()
                }

                else -> {}
            }
        }

    private val choosePictureResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                populateImage(uri)
            }
        }


    private fun showRationaleDialog() {
        val permissionDialog = AlertDialog.Builder(this)
        with(permissionDialog) {
            setTitle("Доступ к камере")
            setMessage("Разрешите доступ к камере для загрузки фото милого котика")
            setPositiveButton("Дать доступ") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            setNegativeButton("Отмена") { _, _ ->
            }
            show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        buttonEdit = findViewById(R.id.button4)
        txtName = findViewById(R.id.textview_name)
        txtSurname = findViewById(R.id.textview_surname)
        txtAge = findViewById(R.id.textview_age)

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

        imageView.setOnClickListener {
            showDialog(this)
        }

        buttonEdit.setOnClickListener {
            openFillFormActivity()
        }

    }

    private fun openFillFormActivity() {
        launcherEditProfile.launch(
            ProfileInfo(
                name = getCurrentName(),
                surname = getCurrentSurname(),
                age = getCurrentAge()
            )
        )
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageView.tag = uri
    }

    private fun openSenderApp() {
        val intent = Intent()

        intent.apply {
            setAction(Intent.ACTION_SEND)
            putExtra(
                Intent.EXTRA_TEXT,
                "${getCurrentName()} ${getCurrentSurname()}, ${getCurrentAge()}"
            )
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, imageView.tag as Uri)
            setPackage("org.telegram.messenger")
        }

        runCatching { startActivity(intent) }
            .onFailure {
                Toast.makeText(this, "Not found application Telegram!", Toast.LENGTH_SHORT).show()
            }
    }


    private fun showDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle("Выберите действие")
            setNegativeButton("Отмена") { _, _ ->
            }
            setItems(imageActions) { _, which ->
                when (which) {
                    TAKE_PHOTO -> takePhoto()
                    CHOSE_PICTURE -> choosePicture()
                }
            }
        }
        builder.show()
    }

    private fun choosePicture() {
        choosePictureResult.launch("image/*")
    }

    private fun takePhoto(needCheckPermission: Boolean = true) {
        if (needCheckPermission) {
            val isGranted =
                ContextCompat.checkSelfPermission(this, permission.CAMERA)
            if (isGranted == PackageManager.PERMISSION_GRANTED) {
                showDefaultImage()
            } else {
                permissionCamera.launch(permission.CAMERA)
            }
        } else {
            showDefaultImage()
        }
    }

    private fun showDefaultImage() {
        imageView.setImageResource(R.drawable.cat)
    }

    companion object ProfileOptions {
        val imageActions = arrayOf("Сделать фото", "Выбрать фото")
        const val TAKE_PHOTO = 0
        const val CHOSE_PICTURE = 1

        data class ProfileInfo(
            val name: String?,
            val surname: String?,
            val age: Int?
        ) : Serializable

    }


}