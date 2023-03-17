package otus.gpb.homework.activities

import Profile
import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar


class ProfileContract: ActivityResultContract<Profile, Profile?>()
{
    override fun createIntent(context: Context, input: Profile): Intent {
        return Intent(context, FillFormActivity::class.java).apply {
            putExtra("profile", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Profile? =
        if (resultCode == Activity.RESULT_OK) intent?.extras?.getParcelable("profile") else null
}



class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

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

        val cameraPerm = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted ->
            if (isGranted) imageView.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.cat))
            else
                if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    val builderSettings = AlertDialog.Builder(this@EditProfileActivity).apply {
                        setTitle("В настройки")
                        setCancelable(false)
                        setPositiveButton(
                            "Открыть настройки”"
                        ) { dialog, _ ->
                            dialog.cancel()
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this@EditProfileActivity.packageName, null)
                            )
                            startActivity(intent)
                        }
                    }
                    with(builderSettings)
                    {
                        create()
                        show()
                    }
            }
        }

        val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent())
        {
            imageUri = it
            it?.let { populateImage(it) }
        }

        val profileContract = registerForActivityResult(ProfileContract()){
            it?.let {
                findViewById<TextView>(R.id.textview_name).apply { text = it.name }
                findViewById<TextView>(R.id.textview_surname).apply { text = it.surname }
                findViewById<TextView>(R.id.textview_age).apply { text = it.age.toString() }
                profile = it
            }
        }

        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {

            val builderChoiceSource = AlertDialog.Builder(this).apply {
                setTitle("Выберите действие")
                setCancelable(true)
                setItems(arrayOf("Сделать фото", "Выбрать фото")) { _, choice ->
                    when (choice) {
                        0 -> {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                val builderInfo = AlertDialog.Builder(this@EditProfileActivity).apply {
                                    setTitle("Ну разрешите же")
                                    setMessage("Доступ к камере необходим, чтобы запечатлить Вашу прекрасную физиономию!")
                                    setCancelable(false)
                                    setPositiveButton(
                                        "Дать доступ"
                                    ) { dialog, _ ->
                                        dialog.cancel()
                                        cameraPerm.launch(Manifest.permission.CAMERA)
                                    }
                                    setNegativeButton(
                                        "Отмена")
                                    { dialog, _ ->
                                        dialog.cancel()
                                    }
                                }
                                with(builderInfo)
                                {
                                    create()
                                    show()
                                }
                            } else cameraPerm.launch(Manifest.permission.CAMERA)
                        }
                        1 -> {
                            try {
                                galleryContract.launch("image/*")
                            } catch (e: ActivityNotFoundException) {
                                Toast.makeText(this@EditProfileActivity,"Что-то пошло нет так...",Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            }

            with(builderChoiceSource)
            {
                create()
                show()
            }

        }

        val editProfile = findViewById<Button>(R.id.button4)
        editProfile.setOnClickListener {
            profileContract.launch(Profile())
        }

    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        //val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageURI(uri)//.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {

        if ((imageUri != null) && (profile != null))
        {
            val intent = Intent(Intent.ACTION_SEND).apply {
                setPackage("org.telegram.messenger")
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                putExtra(Intent.EXTRA_TEXT, "${profile!!.name} ${profile!!.surname} ${profile!!.age}")
            }
            try {
                startActivity(intent)
            }
            catch (e: ActivityNotFoundException)
            {
                Toast.makeText(this, "Приложение Telegram не найдено", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(this, "Выберите фото и заполните профиль", Toast.LENGTH_SHORT).show()

    }

}