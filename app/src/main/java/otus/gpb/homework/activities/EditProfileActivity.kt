package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    //unnecessary user's PersonDTO object
    private val user = PersonDTO(null, null, null)

    private lateinit var imageView: ImageView

    //Uri of the image to be share with Telegram
    private var imageViewUri: Uri? = null

    //counter for the number of requests for camera permission
    private var cameraPermissionCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageView = binding.imageviewPhoto

        binding.imageviewPhoto.setOnClickListener {
            createSingleChoiceDialog()
        }


        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    binding.textviewName.text =
                        result.data?.getStringExtra("name")
                    binding.textviewSurname.text =
                        result.data?.getStringExtra("lastName")
                    binding.textviewAge.text =
                        result.data?.getStringExtra("age")
                    user.name = result.data?.getStringExtra("name")
                    user.lastName = result.data?.getStringExtra("lastName")
                    user.age = result.data?.getStringExtra("age")
                }
            }

        binding.toolbar.apply {
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

        binding.button4.setOnClickListener {
            activityResultLauncher?.launch(Intent(this, FillFormActivity::class.java))
        }

    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                populateImage(imageUri)
                imageViewUri = imageUri
            }
        }
//"В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку"

    private fun openSenderApp() {

        try {

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageViewUri)
            shareIntent.putExtra(Intent.EXTRA_TEXT, "${user.name}\n${user.lastName}\n${user.age}")
            shareIntent.type = "image/*"
            shareIntent.setPackage("org.telegram.messenger")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            }
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(
                this, getString(R.string.no_Telegram), Toast.LENGTH_LONG
            ).show()
        }
    }

    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                val dontShowAgain = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                if (dontShowAgain) {
                    if (cameraPermissionCounter == 0) {
                        cameraPermissionCounter += 1
                    } else if (cameraPermissionCounter == 1) {
                        cameraPermissionCounter += 1
                        createSimpleDialog()
                    } else if (cameraPermissionCounter > 1) {
                        toSettingsDialog()
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (cameraPermissionCounter == 1) {
                            cameraPermissionCounter += 1
                            createSimpleDialog()
                        } else {
                            toSettingsDialog()
                        }
                    } else toSettingsDialog()
                }
            } else imageView.setImageResource(R.drawable.cat)
        }

    private fun createSingleChoiceDialog() {
        val checkedItem = intArrayOf(-1)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setSingleChoiceItems(
            arrayOf(
                getString(R.string.make_photo), getString(R.string.choose_photo)
            ), checkedItem[0]
        ) { dialog, which ->
            checkedItem[0] = which
            for (i in checkedItem) {
                if (i == 0) {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        permissionRequestLauncher.launch(Manifest.permission.CAMERA)
                    } else {
                        imageView.setImageResource(R.drawable.cat)
                    }
                } else takePictureLauncher.launch("image/*")

                dialog.dismiss()
            }
        }
        alertDialogBuilder.show()
    }

    private fun createSimpleDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.to_permissions_title))
        builder.setMessage(getString(R.string.permission_explanation))

        builder.setNegativeButton(getString(R.string.cancel_dialog)) { dialog, i ->
            Toast.makeText(this, getString(R.string.no_camera_permission), Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        builder.setPositiveButton(getString(R.string.allow_access)) { dialog, i ->
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionRequestLauncher.launch(Manifest.permission.CAMERA)
            }
        }
        builder.show()
    }

    private fun toSettingsDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setNeutralButton(getString(R.string.open_settings)) { dialog, i ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                val uri = Uri.fromParts("package", packageName, null)
                data = uri
            }
            startActivity(intent)
        }
        builder.show()
    }
}