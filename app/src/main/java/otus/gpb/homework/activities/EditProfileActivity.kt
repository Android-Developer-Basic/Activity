package otus.gpb.homework.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var alertChoices: Array<String>

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::permissionResultHandler
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById<ImageView?>(R.id.imageview_photo).apply {
            setOnClickListener { showAlertDialog() }
        }

        alertChoices = arrayOf(
            resources.getString(R.string.choose_photo),
            resources.getString(R.string.make_photo)
        )

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

    private fun permissionResultHandler(granted: Boolean) {
        if (granted) {
            findViewById<ImageView>(R.id.imageview_photo)
                .setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.cat, null))
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.why_we_need_permission)
                    .setPositiveButton(R.string.give_permission
                    ) { _, _ -> requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA) }
                    .setNegativeButton(R.string.cancel_give_permission, null)
                    .show()
            }
        }
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.alert_title))
            .setItems(alertChoices) { _, index: Int ->
                    when (index) {
                        1 -> requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        2 -> {}
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
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }
}