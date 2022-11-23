package otus.gpb.homework.activities

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Intent
import android.provider.Settings
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var user: UserDTO
    private lateinit var fName: TextView
    private lateinit var lName: TextView
    private lateinit var age: TextView
    private var counterPermission = 1
    private var uriPicture: Uri? = null

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::permissionResult
    )

    private val resultContract = registerForActivityResult(ActivityResultContracts.GetContent()) { it
        if (it != null) {
            uriPicture = it
            populateImage(it)}
    }

    private val resultContractFillFormActivity = registerForActivityResult(ContractFillFormActivity()){ result ->
        if (result != null) {
            user = result
            fName.text = result.name
            lName.text = result.surname
            age.text = result.age
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        fName = findViewById(R.id.textview_name)
        lName = findViewById(R.id.textview_surname)
        age = findViewById(R.id.textview_age)

        imageView = findViewById<ImageView?>(R.id.imageview_photo).apply {
            setOnClickListener { createChoiseDialog() }
        }

        button = findViewById<Button>(R.id.bt_edit_profile).apply {
            setOnClickListener { resultContractFillFormActivity.launch("") }
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
    private fun permissionResult(isGranted: Boolean) = if (isGranted) {
        findViewById<ImageView>(R.id.imageview_photo)
            .setImageDrawable(this.getDrawable(R.drawable.cat))
        imageView.tag = this.uriPicture
        this.uriPicture = imageView.tag as Uri?
    } else {
        when(counterPermission) {
            1 -> {}
            2 -> {createNeedPermissionDialog()}
            else -> {createSettingsDialog()}
        }
        this.counterPermission += 1
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
                    0 -> choosePhotoFromGallery()
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
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                clipData = ClipData.newRawUri(null, uriPicture)
                putExtra(Intent.EXTRA_STREAM,uriPicture)
                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
              putExtra(Intent.EXTRA_TEXT, "Name: ${fName.text}\nSurname: ${lName.text}\nAge: ${age.text}")
            }
            startActivity(Intent.createChooser(intent, null))
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(this, "Telegram не установлен на вашем устройстве", Toast.LENGTH_LONG).show()
        }
    }
}