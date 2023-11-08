package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class EditProfileActivity : AppCompatActivity() {

    private var deniedOnce = false

    private val takePictureContract = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        imageView.setImageBitmap(bitmap)
    }

    private val permissionCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> {
                imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cat))
                //takePictureContract.launch(null)
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                //Permission Denied And Don't Ask Again
                deniedOnce = false
                alertQuestionSettingsShow()
            }
            else -> {
                deniedOnce = true
                showToast(getString(R.string.permission_camera_denied))
            }
        }
    }

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)

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
            showPhotoSelectionDialog()
        }
    }

    private fun showPhotoSelectionDialog(){
        val options = arrayOf(getString(R.string.make_photo), getString(R.string.choose_photo))
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_dialog_choice))
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> requestCameraPermission()
                    1 -> selectPhotoFromGallery()
                }
            }
            .show()
    }

    private fun selectPhotoFromGallery(){

    }

    private fun requestCameraPermission(){
        val isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (isGranted) {
            takePictureContract.launch(null)
            return
        }

        if (deniedOnce){
            alertDialogExplanation()
            return
        }

        permissionCamera.launch(Manifest.permission.CAMERA)
    }

    private fun alertDialogExplanation(){
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_camera_alert_title))
            .setMessage(getString(R.string.permission_camera_description))
            .setPositiveButton(getString(R.string.access)) { dialog, which ->
                permissionCamera.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                showToast(getString(R.string.permission_camera_denied))
            }
            .show()
    }

    private fun alertQuestionSettingsShow(){
        //TODO в задании надо было показать одну кнопку пользователю, без альтернативы.
        // я решил пойти на встречу пользователю.
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_camera_alert_title))
            .setMessage(getString(R.string.open_settings_alert_description))
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                openSettings()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, which ->
                showToast(getString(R.string.cant_open_settings))
            }
            .show()
    }

    private fun openSettings(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            val uri =Uri.fromParts("package", packageName, null)
            data = uri
        }
        startActivity(intent)
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

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}