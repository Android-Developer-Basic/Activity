package otus.gpb.homework.activities


import android.Manifest
import android.R.attr.bitmap
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Images
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toIcon
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding


class EditProfileActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 101
    private val PERMISSION_STORAGE_CODE = 121
    lateinit var pLauncher: ActivityResultLauncher<String>
    lateinit var binding: ActivityEditProfileBinding
    lateinit var utis: Uri

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<Toolbar>(binding.toolbar.id).apply {
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

        registerPermissionListener()

        binding.imageviewPhoto.setOnClickListener {
            val items = arrayOf("Сделать фото", "Выбрать фото")

            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.titleDialog))
                .setItems(items) { dialog, which ->
                    val item = items[which]
                    if (item.equals("Сделать фото")) {
                        checkForPermissions(
                            Manifest.permission.CAMERA,
                            "camera",
                            CAMERA_REQUEST_CODE
                        )

                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                                requestPermissions(permissions, PERMISSION_STORAGE_CODE)
                            } else {
                                chooseImageGallery();
                            }
                        } else {
                            chooseImageGallery();

                        }
                    }

                }
                .show()
        }

        var getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val r = result.data
                    binding.textviewAge.text = r?.getStringExtra("age")
                    Toast.makeText(this, r?.getStringExtra("age").toString(), Toast.LENGTH_SHORT).show()
                    binding.textviewName.text = r?.getStringExtra("textviewName")
                    binding.textviewSurname.text = r?.getStringExtra("textviewSurname")


                }

            }

        binding.editProfile.setOnClickListener {
            getContent.launch(Intent(this, FillFormActivity::class.java))
        }

    }

    private fun chooseImageGallery() {
        pLauncher.launch("image/*")
    }

    private fun registerPermissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                utis = it
                populateImage(it)
            }
        }
    }


    private fun checkForPermissions(permissiom: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permissiom
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(this, "$name permission granted", Toast.LENGTH_SHORT).show()

                }
                shouldShowRequestPermissionRationale(permissiom) -> showDialog(
                    permissiom,
                    requestCode
                )

                else -> ActivityCompat.requestPermissions(this, arrayOf(permissiom), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "$name permission refused", Toast.LENGTH_SHORT).show()
                showDialogSettings()
            } else {
                binding.imageviewPhoto.setImageResource(R.drawable.cat)
                Toast.makeText(this, "$name permission granted", Toast.LENGTH_SHORT).show()

            }
        }
        when (requestCode) {
            CAMERA_REQUEST_CODE -> innerCheck("camera")
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showDialog(permissiom: String, requestCode: Int) {
        val builder = MaterialAlertDialogBuilder(this)

        builder.apply {
            setMessage("Нам нужна ваша КАМЕРА!(пожалуйста)")
            setTitle("Дай камеру")
            setPositiveButton("Дать доступ") { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@EditProfileActivity,
                    arrayOf(permissiom),
                    requestCode
                )

            }
            setNegativeButton("Отмена") { dialog, which ->

            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showDialogSettings() {
        val builder = MaterialAlertDialogBuilder(this)

        builder.apply {
            setPositiveButton("Открыть настройки") { dialog, which ->
                startActivity(Intent(Settings.ACTION_SETTINGS))

            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        binding.imageviewPhoto.setImageBitmap(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun openSenderApp() {

        val sendIntent = Intent()

        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Фамилия: " +
            binding.textviewSurname.text.toString() + System.lineSeparator().toString() +
            "Имя: "    + binding.textviewName.text + System.lineSeparator().toString() +
            "Возраст: "    + binding.textviewAge.text)
        sendIntent.putExtra(Intent.EXTRA_STREAM, utis )
        sendIntent.type = "image/jpeg";
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(sendIntent, "send"))
    }
}

