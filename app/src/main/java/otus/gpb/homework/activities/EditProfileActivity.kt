package otus.gpb.homework.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding
import otus.gpb.homework.activities.dto.User

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editProfileBinding: ActivityEditProfileBinding

    private var user: User? = null
    private var uri: Uri? = null

    private val requestCameraPermissionLauncher = registerForActivityResult(
        RequestPermission(),
        ::permissionResultHandler
    )

    private val getFillForm = registerForActivityResult(CustomActivityResultContract()) {
        if (it != null) {
            user = it
            editProfileBinding.textviewName.text = it.firstName
            editProfileBinding.textviewSurname.text = it.lastName
            editProfileBinding.textviewAge.text = it.age
        }
    }

    private val getPictureFromLocalLibrary = registerForActivityResult(GetContent()) { uri ->
        if (uri != null) {
            this.uri = uri
            populateImage(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        editProfileBinding.imageviewPhoto.setOnClickListener { showAlertDialog() }
        editProfileBinding.button4.setOnClickListener { getFillForm.launch("") }

        editProfileBinding.toolbar.apply {
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

    private fun permissionResultHandler(granted: Boolean) {
        if (granted) {
            setDefaultPicture()
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.why_we_need_permission)
                    .setPositiveButton(
                        R.string.give_permission
                    ) { _, _ -> requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA) }
                    .setNegativeButton(R.string.cancel_give_permission, null)
                    .show()
            }
        }
    }

    private fun setDefaultPicture() {
        editProfileBinding.imageviewPhoto
            .setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cat))
    }

    private fun showAlertDialog() {
        val alertChoices = arrayOf(
            resources.getString(R.string.choose_photo),
            resources.getString(R.string.make_photo)
        )
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.alert_title))
            .setItems(alertChoices) { _, index: Int ->
                when (index) {
                    0 -> getPictureFromLocalLibrary.launch("image/*")
                    1 -> requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    else -> {}
                }
            }
            .show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        editProfileBinding.imageviewPhoto.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                type = "image/*"
                user?.let {
                    putExtra(Intent.EXTRA_TEXT, it.toString())
                }
                uri?.let {
                    putExtra(Intent.EXTRA_STREAM, it)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
            }
            startActivity(intent)
        } catch (e: NameNotFoundException) {
            Toast.makeText(
                this,
                "No Telegram app found on system",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}