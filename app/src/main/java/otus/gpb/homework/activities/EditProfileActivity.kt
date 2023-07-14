package otus.gpb.homework.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }
    private lateinit var imageUri: Uri
    val userData = PersonData()

    private val addPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { photo ->
            photo?.let { uri ->
                populateImage(uri)
                imageUri = uri
            }
        }

    private val makePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            binding.imageviewPhoto.setImageBitmap(bitmap)
        }

    @SuppressLint("UseCompatLoadingForDrawables")
    private val makePhotoPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> {
                    binding.imageviewPhoto.setImageDrawable(applicationContext.getDrawable(R.drawable.cat))
                }

                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    })
                }

                else -> {
                    Toast.makeText(this, "Разрешение не было получено", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private val fillFormActivityContract =
        registerForActivityResult(ContractFillFormActivity()) { result ->
            if (result == null) return@registerForActivityResult
            userData.name = result.name
            userData.surName = result.surName
            userData.age = result.age
            binding.textviewName.text = result.name
            binding.textviewSurname.text = result.surName
            binding.textviewAge.text = result.age.toString()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rationaleDialog = MaterialAlertDialogBuilder(this)
            .setTitle("Права доступа")
            .setMessage("Камера нужна для усановки фото.")
            .setNeutralButton("Дать доступ") { _, _ ->
                makePhotoPermission.launch(Manifest.permission.CAMERA)
            }
            .setPositiveButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }

        val allertDialog = MaterialAlertDialogBuilder(this)
            .setTitle("Фото")
            .setMessage("Выберите")
            .setNeutralButton("Выбрать фото") { _, _ ->
                addPhoto.launch("image/*")
            }
            .setPositiveButton("Сделать фото") { _, _ ->
                makePhotoPermission.launch(Manifest.permission.CAMERA)
            }

        binding.imageviewPhoto.setOnClickListener {
            allertDialog.show()
        }

        binding.button4.setOnClickListener {
            fillFormActivityContract.launch(PersonData(
                userData.name,
                userData.surName,
                userData.age
            ))
        }

        binding.toolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.send_item) openSenderApp()
            true
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

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        binding.imageviewPhoto.setImageBitmap(
            BitmapFactory.decodeStream(
                contentResolver.openInputStream(
                    uri
                )
            )
        )
    }

    private fun openSenderApp() {
        try {
            startActivity(Intent().apply {
                `package` = "org.telegram.messenger"
                putExtra(Intent.EXTRA_TEXT, "${userData.name}\n${userData.surName}\n${userData.age}")
                type = "image/*"
                imageUri.let {
                    intent.putExtra(Intent.EXTRA_STREAM, imageUri)
                }
            })
        }catch (exception: ActivityNotFoundException){
            Toast.makeText(applicationContext, "Telegram не установлен", Toast.LENGTH_SHORT).show()
        }

    }
}