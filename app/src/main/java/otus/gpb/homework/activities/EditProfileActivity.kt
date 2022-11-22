package otus.gpb.homework.activities

import android.content.Intent
import android.provider.Settings
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var counterPermission = 1

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::permissionResult
    )

    private val resultContract = registerForActivityResult(ActivityResultContracts.GetContent()) { it
        if (it != null) populateImage(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById<ImageView?>(R.id.imageview_photo).apply {
            setOnClickListener { createChoiseDialog() }
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
    private fun permissionResult(isGranted: Boolean) {
        if (isGranted) {
            findViewById<ImageView>(R.id.imageview_photo)
                .setImageDrawable(this.getDrawable(R.drawable.cat))
        } else {
            when(counterPermission) {
                1 -> {}
                2 -> {createNeedPermissionDialog()}
                else -> {createSettingsDialog()}
            }
            this.counterPermission += 1
        }
    }

    private fun createSettingsDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.settings_title))
            setMessage(resources.getString(R.string.settings_message))
            setPositiveButton(resources.getString(R.string.open_settings)) { _, _ ->
                startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        val uri = Uri.fromParts("package", packageName, null)
                        data = uri
                    }
                )
            }
            show()
        }
    }

    private fun createNeedPermissionDialog(){
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            MaterialAlertDialogBuilder(this).apply  {
                setTitle(R.string.proof_title)
                setMessage(R.string.proof_of_need_permission)
                setPositiveButton(R.string.allow_permission) { _, _ ->
                    requestCameraPermission()
                }
                setNegativeButton(R.string.cancel_permission, null)
                show()
            }
        }
    }
    private fun createChoiseDialog() {
        val singleItems = arrayOf(
            resources.getString(R.string.ch_photo),
            resources.getString(R.string.mk_photo)
        )
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.title))
            setItems(singleItems) { _, index: Int ->
                when (index) {
                    0 -> {choosePhotoFromGallery()}
                    1 -> requestCameraPermission()
                    else -> {}
                }
            }
            show()
        }
    }

    private fun requestCameraPermission() {
        requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    private fun choosePhotoFromGallery() {
        resultContract.launch("image/*")
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