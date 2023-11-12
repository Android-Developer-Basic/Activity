package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity(), OnClickListener {

    private lateinit var imageView: ImageView

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    showRationalDialog()
                } else {
                    openAppSettings()
                }
            }
        }

    private val selectFromGallery =
        registerForActivityResult(CustomActivityResultContract()) { uri ->
            uri?.let {
                populateImage(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener(this)

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

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imageview_photo -> openAlertDlg()
            R.id.make_picture_btn -> createPhoto()
            R.id.choose_photo_btn -> selectPhotoFromGallery()
        }
    }

    private fun selectPhotoFromGallery() {
        selectFromGallery.launch(null)
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:$packageName")
        }

        startActivity(intent)
    }

    private fun showRationalDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Attention")
            setMessage("To make photo we need permissions")
            setCancelable(false)
            setPositiveButton("Give permission") { _, _ ->
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        }.create().show()
    }

    private fun createPhoto() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        findViewById<ImageView>(R.id.imageview_photo).setImageResource(R.drawable.cat)
    }

    private fun openAlertDlg() {
        val customView = layoutInflater.inflate(R.layout.custom_alert_dlg, null)

        MaterialAlertDialogBuilder(this).apply {
            setTitle("Choose option")
            setMessage("Create photo or select it from photo gallery")
            setCancelable(false)
            setView(customView)
        }.create().show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO(
            """В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. 
            |В качестве extras передайте заполненные строки и картинку""".trimMargin()
        )
    }
}