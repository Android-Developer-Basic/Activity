package otus.gpb.homework.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private val cameraContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it)
                setDefaultImage()
            else
                if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                    showSettingsDialog()
        }

    private val imageContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
//                imageURI = it
                populateImage(it)
            }
        }

    private val fillFormContract = registerForActivityResult(FillFormContract()) { result ->
        updateFields(result)
    }

    private var imageURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater).also {
            setContentView(it.root)
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

        binding.imageviewPhoto.setOnClickListener {
            showAlertDialog()
        }

        binding.button4.setOnClickListener {
            fillFormContract.launch("")
        }
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.alert_title))
            setNeutralButton(resources.getString(R.string.make_photo)) { _, _ ->
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                    showRationaleDialog()
                else
                    cameraContract.launch(Manifest.permission.CAMERA)
            }
            setPositiveButton(resources.getString(R.string.choose_photo)) { _, _ ->
                imageContract.launch("image/*")
            }
        }.show()
    }

    private fun showRationaleDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.use_camera))
            setMessage(resources.getString(R.string.permission_message))
            setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
            }
            setPositiveButton(resources.getString(R.string.access)) { _, _ ->
                cameraContract.launch(Manifest.permission.CAMERA)
            }
        }.show()
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.photo_permission))
            setPositiveButton(resources.getString(R.string.open_settings)) { _, _ ->
                startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        val uri = Uri.fromParts("package", packageName, null)
                        data = uri
                    }
                )
            }
        }.show()
    }

    private fun setDefaultImage() {
        binding.imageviewPhoto.setImageDrawable(
            AppCompatResources.getDrawable(
                this, R.drawable.cat
            )
        )
    }

    private fun updateFields(user: UserModel?) {
        user?.let {
            val (fName, lName, age) = it

            binding.textviewName.text = fName
            binding.textviewSurname.text = lName
            binding.textviewAge.text = age.toString()
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        binding.imageviewPhoto.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                type = "image/*"
                imageURI?.let {
                    putExtra(Intent.EXTRA_STREAM, imageURI)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Name: ${binding.textviewName.text}\nSurName: ${binding.textviewSurname.text}\nAge: ${binding.textviewAge.text}"
                )
            }

            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Telegram не установлен", Toast.LENGTH_SHORT).show()
        }
    }
}
