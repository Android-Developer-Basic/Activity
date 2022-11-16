package otus.gpb.homework.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.dto.User

class EditProfileActivity : AppCompatActivity(), OnClickListener {

    private lateinit var imageView: ImageView
    private lateinit var alertChoices: Array<String>
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var age: TextView
    private lateinit var editBtn: Button
    private lateinit var user: User
    private lateinit var uri: Uri

    private val requestCameraPermissionLauncher = registerForActivityResult(
        RequestPermission(),
        ::permissionResultHandler
    )

    private val getFillForm = registerForActivityResult(CustomActivityResultContract()) {
        if (it !== null) {
            user = it
            firstName.text = it.firstName
            lastName.text = it.lastName
            age.text = it.age
        }
    }

    private val getPictureFromLocalLibrary = registerForActivityResult(GetContent()) { uri ->
        if (uri !== null) {
            this.uri = uri
            populateImage(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        firstName = findViewById(R.id.textview_name)
        lastName = findViewById(R.id.textview_surname)
        age = findViewById(R.id.textview_age)
        editBtn = findViewById(R.id.button4)

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
                    .setPositiveButton(
                        R.string.give_permission
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
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val telegramPackageName = "org.telegram.messenger"
        try {
            this.packageManager.getPackageInfo(
                telegramPackageName,
                PackageManager.GET_ACTIVITIES
            )
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage(telegramPackageName)
                putExtra("Extra", user)
                putExtra("Picture", uri)
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button4 -> getFillForm.launch("")
            R.id.imageview_photo -> showAlertDialog()
        }
    }
}