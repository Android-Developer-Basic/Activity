package otus.gpb.homework.activities

import android.Manifest
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

private const val cameraPermission = Manifest.permission.CAMERA

class EditProfileActivity : AppCompatActivity() {

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
            showDialogChoseActionPhoto()
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            profileContract.launch(profileData)
        }

    }

    private lateinit var imageView: ImageView
    private var permissionRequest = 0
    private var profileData = DataProfile("", "", "")

    //Contacts
    private val takePictureContract =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            imageView.setImageBitmap(it)
        }

    private val profileContract = registerForActivityResult(ProfileContract()) { result ->
        result?.let {
            profileData = it
            findViewById<TextView>(R.id.textview_name).text = profileData.name
            findViewById<TextView>(R.id.textview_surname).text = profileData.surname
            findViewById<TextView>(R.id.textview_age).text = profileData.age

            sendDataTelegram = "${profileData.name}\n${profileData.surname}\n${profileData.age}"
        }
    }
    private val getContentContract = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { result ->
        result?.let {
            populateImage(it)
        }
    }

    private val permissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    takePictureContract.launch(null)
                }

                !shouldShowRequestPermissionRationale(cameraPermission) && permissionRequest > 1 -> {
                    showDialogSettings()
                }

                else -> {
                    permissionRequest++
                }
            }
        }

    private var uriImage: Uri? = null
    private var sendDataTelegram: String = ""

    //Dialogs
    private fun showDialogChoseActionPhoto() {

        val options = arrayOf(
            resources.getString(R.string.take_photo),
            resources.getString(R.string.select_photo)
        )

        MaterialAlertDialogBuilder(this).apply {

            setTitle(resources.getString(R.string.title))
            setItems(options) { _, which ->
                when (which) {
                    0 -> checkPermissionGranted()
                    1 -> selectPhoto()
                }
            }
            show()
        }

    }

    private fun showDialogExplanation() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.title_explanation))
            setMessage(resources.getString(R.string.explanation))

            setPositiveButton(R.string.get_access) { _, _ ->
                permissionCamera.launch(cameraPermission)
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
                permissionRequest++
            }
            show()
        }
    }

    private fun showDialogSettings() {
        AlertDialog.Builder(this).apply {
            setMessage(R.string.setting_message)
            setPositiveButton(R.string.open_settings) { dialog, _ ->
                dialog.dismiss()
                openSettings()
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }


    private fun selectPhoto() {
        getContentContract.launch(resources.getString(R.string.selected_photo_pattern))
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            val uri =
                Uri.fromParts(resources.getString(R.string.package_settings), packageName, null)
            data = uri
        }
        startActivity(intent)
    }

    private fun checkPermissionGranted() {

        if (permissionRequest == 1) {
            showDialogExplanation()
            return
        }

        val isGranted = ContextCompat.checkSelfPermission(
            this,
            cameraPermission
        ) == PackageManager.PERMISSION_GRANTED
        if (isGranted) {
            takePictureContract.launch(null)
            return
        }

        permissionCamera.launch(Manifest.permission.CAMERA)


    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        uriImage = uri
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun isTelegramInstalled(context: Context, packageName: String): Boolean {
        val packageManager = context.packageManager
        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        for (appInfo in installedApps) {
            if (appInfo.packageName == resources.getString(R.string.package_telegram)) {
                return true
            }
        }

        return false
    }

    private fun openSenderApp() {
        if (isTelegramInstalled(this, resources.getString(R.string.package_telegram))) {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = resources.getString(R.string.selected_photo_pattern)
                putExtra(Intent.EXTRA_STREAM, uriImage)
                putExtra(Intent.EXTRA_TEXT, sendDataTelegram)
                setPackage(resources.getString(R.string.package_telegram))
            }
            startActivity(sendIntent)
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                resources.getString(R.string.text_snackbar_telegram),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}