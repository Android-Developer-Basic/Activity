package otus.gpb.homework.activities

import android.Manifest
import android.Manifest.permission
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import otus.gpb.homework.activities.databinding.ActivityEditProfileBinding
import android.provider.Settings
import androidx.core.content.ContextCompat

class EditProfileActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private val arrayButtonsNames = arrayOf("Сделать фото", "Выбрать фото")
    private var secondRequest : Boolean = false
    private val permissionCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> {
                    takePhoto(false)
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    if( !secondRequest ) {
                        NeedCameraDialog()
                        secondRequest = true
                    }
                    else
                    {
                        val builder = AlertDialog.Builder(this)
                        builder.apply {
                            setTitle("Доступ к камере")
                            setPositiveButton("Открыть настройки", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    startActivity(intent)
                                }
                            })
                            create()
                            show()
                        }
                    }
                }
                else -> {}
            }
        }

    private val choosePictureResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                populateImage(uri)
                imageUri = uri
            }
        }

    private val launcher = registerForActivityResult(ContractFillFormActivity()){ userData ->
        if (userData != null){
            binding.textviewName.text = userData.name
            binding.textviewSurname.text = userData.surname
            binding.textviewAge.text = userData.age
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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

        binding.imageviewPhoto.setOnClickListener() {
            val builder = AlertDialog.Builder(this)

            with(builder) {
                setTitle("Добавить фото")
                setNegativeButton("Отмена") { _, _ ->
                }
                setItems(arrayOf("Сделать фото", "Выбрать фото")) { _, which ->
                    when (which) {
                        0 -> takePhoto()
                        1 -> choosePictureResult.launch("image/*")
                    }
                }
            }
            builder.show()
        }

        binding.button4.setOnClickListener(){
            launcher.launch(UserData(binding.textviewName.text.toString(),
                binding.textviewSurname.text.toString(),
                binding.textviewAge.text.toString()) )
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
        val intent = Intent(Intent.ACTION_SEND).apply {
            setPackage("org.telegram.messenger")
            setType("image/*")
            if (imageUri != null) putExtra(Intent.EXTRA_STREAM, imageUri)
            putExtra(Intent.EXTRA_TEXT, "Имя : ${binding.textviewName.text}\nФамилия: ${binding.textviewSurname.text}\nВозраст: ${binding.textviewAge.text}")
        }
        startActivity(intent)
    }

    private fun takePhoto(checkPermission: Boolean = true) {
        if (checkPermission) {
            val isGranted = ContextCompat.checkSelfPermission(this, permission.CAMERA)
            when (isGranted) {
                PackageManager.PERMISSION_GRANTED -> imageView.setImageDrawable(getDrawable(R.drawable.cat))
                else -> permissionCamera.launch(permission.CAMERA)
            }
        } else {
            imageView.setImageDrawable(getDrawable(R.drawable.cat))
        }
    }

    private fun NeedCameraDialog() {
        val permissionDialog = AlertDialog.Builder(this)

        with(permissionDialog) {
            setTitle("Доступ к камере")
            setMessage("Доступ к камере необходим чтобы сделать ваше фото")
            setPositiveButton("Дать доступ") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            setNegativeButton("Отмена") { _, _ -> }
            show()
        }
    }
}