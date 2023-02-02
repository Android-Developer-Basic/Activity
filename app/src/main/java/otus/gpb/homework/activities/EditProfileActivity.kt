package otus.gpb.homework.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private var imageUri: Uri? = null

    private val contractFillA= registerForActivityResult(ContractFillFormActivity()){ result ->
        result?.let{
            binding.textviewName.text = it.first_name
            binding.textviewSurname.text = it.last_name
            binding.textviewAge.text = it.age
        }
    }

    private fun dialogRationale() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.rationale_dialog))
            setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
            }
            setPositiveButton(resources.getString(R.string.give_access)) { _, _ ->
                cameraContract.launch(Manifest.permission.CAMERA)
            }
        }.show()
    }

    private val cameraContract = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it)
            // если разрешение есть ставим картинку
            binding.imageviewPhoto.setImageResource(R.drawable.cat)
        else
            // если разрешения нет выводим сообщение к настройками
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                dialogSetting()
    }

    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            populateImage(it)
        }
    }

    private fun dialogSetting(){
        MaterialAlertDialogBuilder(this).apply {
            setTitle(resources.getString(R.string.repeat_request_c))
            setPositiveButton(resources.getString(R.string.open_setting)) { _, _ ->
                startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        val uri = Uri.fromParts("package", packageName, null)
                        data = uri
                    }
                )
            }
        }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEdit.setOnClickListener {
            contractFillA.launch("")
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

        binding.imageviewPhoto.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.set_photo))
                .setNeutralButton(resources.getString(R.string.take_photo)) { dialog, which ->
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                        dialogRationale()
                    else
                        cameraContract.launch(Manifest.permission.CAMERA)
                }
                .setPositiveButton(resources.getString(R.string.pick_photo)) { dialog, which ->
                    galleryContract.launch("image/*")
                }
                .show()
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
                type = "*/*"
                imageUri?.let {
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                    putExtra(Intent.EXTRA_TEXT,
                        "Name: ${binding.textviewName.text}\n" +
                                "SurName: ${binding.textviewSurname.text}\n" +
                                "Age: ${binding.textviewAge.text}"
                    )
                }
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.app_absent, Toast.LENGTH_LONG).show()
        }
    }
}