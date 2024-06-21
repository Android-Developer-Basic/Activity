package otus.gpb.homework.activities

import android.Manifest
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
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding


const val KEY_NAME = "name"
const val KEY_SURNAME = "surname"
const val KEY_AGE = "age"

class EditProfileActivity : AppCompatActivity() {

    private var launchSettings: (() -> Unit)? = ::gotoSettings
    private lateinit var imageView: ImageView
    private lateinit var binding: ActivityEditProfileBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageView = binding.imageviewPhoto

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

        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this).setTitle(resources.getString(R.string.dialogTitle))
                .setItems(resources.getTextArray(R.array.dialogActions)) { _, item ->
                    when (item) {
                        0 -> makePhoto()
                        1 -> getPhoto()
                    }
                }.show()
        }

        binding.editProfile.apply {
            setOnClickListener {
                openFillForm()
            }
        }
    }


    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            when {
                it -> {
                    imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cat))
                    currentImageUri = null
                }

                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> // если "больше не спрашивать"
                    launchSettings?.invoke() ?: run { launchSettings = ::gotoSettings }
            }
        }

    private fun makePhoto() {
        // повторный запрос на использование камеры
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            launchSettings =
                null // чтобы не предлагало пойти в настройки в первый раз после "больше не спрашивать"
            MaterialAlertDialogBuilder(this).setMessage("Очень нужен доступ к камере!")
                .setNegativeButton("Отмена", null)
                .setPositiveButton("Дать доступ") { _, _ ->
                    permission.launch(Manifest.permission.CAMERA)
                }.show()
        } else permission.launch(Manifest.permission.CAMERA)
    }

    private val gallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
        currentImageUri = it
        if (it != null) populateImage(it)
    }

    private fun getPhoto() {
        gallery.launch("image/*")
    }

    private fun openFillForm() {
        launcher.launch(Intent(this, FillFormActivity::class.java).apply {
            putExtra(KEY_NAME, binding.textviewName.text)
            putExtra(KEY_SURNAME, binding.textviewSurname.text)
            putExtra(KEY_AGE, binding.textviewAge.text)
        })
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Отмена редактирования", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }
            if (it.resultCode == RESULT_OK && it.data != null) {
                binding.textviewName.text = it.data?.getStringExtra(KEY_NAME)
                binding.textviewSurname.text = it.data?.getStringExtra(KEY_SURNAME)
                binding.textviewAge.text = it.data?.getStringExtra(KEY_AGE)
            }
        }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        val tg = "org.telegram.messenger"
        val intent: Intent = Intent(Intent.ACTION_SEND).apply {
            `package` = tg
            type = "image/*"
            putExtra(
                Intent.EXTRA_TEXT,
                "${binding.textviewName.text}\n${binding.textviewSurname.text}\n${binding.textviewAge.text}"
            )
            if (currentImageUri != null)
                putExtra(Intent.EXTRA_STREAM, currentImageUri)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Кому ты хочешь отправить? У тебя нет друзей!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun gotoSettings() {
        MaterialAlertDialogBuilder(this).setNeutralButton("Открыть настройки") { _, _ ->
            startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null)
                )
            )
        }.show()
    }


}