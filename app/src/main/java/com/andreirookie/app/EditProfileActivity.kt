package com.andreirookie.app

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.andreirookie.app.dto.User
import com.andreirookie.app.util.ContractEditProfileAndFillForm
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editProfileButton: Button

    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var age: TextView

    private var dummyUser = User(
        name = "Ivan",
        surname = "Ivanov",
        age = 15
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        name = findViewById(R.id.textview_name)
        surname = findViewById(R.id.textview_surname)
        age = findViewById(R.id.textview_age)
        name.text = dummyUser.name
        surname.text = dummyUser.surname
        age.text = dummyUser.age.toString()

        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Фото профиля")
                .setMessage("Варианты действий:")
                .setPositiveButton("Открыть галерею") { _, _ ->
                    galleryResultContract.launch("image/*")
                }
                .setNeutralButton("Сделать фото") { _, _ ->
                    getPhotoFromCamera()
                }
                .show()
        }

        editProfileButton = findViewById(R.id.editProfileButton)
        editProfileButton.setOnClickListener {
            fillFormActivityResultContract.launch(dummyUser)
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
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
//        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            setPackage("org.telegram.messenger")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Intent.EXTRA_TEXT, "${dummyUser.name} ${dummyUser.surname}, ${dummyUser.age}")
                .putExtra(Intent.EXTRA_STREAM, dummyUser.image).type = "image/*"
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            println(e.message)
        }
    }

    private val fillFormActivityResultContract =
        registerForActivityResult(ContractEditProfileAndFillForm()) { user ->
            user ?: return@registerForActivityResult
            fillDummyUserTextData(user)
            populateUserInfo()
        }

    private val cameraPermissionRequestContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
//                    imageView.setImageDrawable(getDrawable(R.drawable.cat))
                    fillDummyUserImageData(Uri.parse("android.resource://$packageName/${R.drawable.cat}"))
                    dummyUser.image?.let { populateImage(it)}

//                    val shouldShowRequestAgain = !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
//                    if (shouldShowRequestAgain) {  }
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    openSettingsViaAlertDialog()
                }
                else -> {
                    Toast.makeText(this, "Попробуйте в след раз", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun openSettingsViaAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setPositiveButton("Открыть настройки") { _ , _ ->
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                startActivity(intent)
            }.show()
    }

    private fun getPhotoFromCamera() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            grantCameraAccessViaAlertDialog()
        } else {
            cameraPermissionRequestContract.launch(Manifest.permission.CAMERA)
        }
    }

    private fun grantCameraAccessViaAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Warning")
            .setMessage("Для использования камеры необходимо получить разрешение")
            .setPositiveButton("Дать доступ") { _, _ ->
                cameraPermissionRequestContract.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Отмена") { text, _ ->
//                text.dismiss() / cancel
            }.show()
    }

    private val galleryResultContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result ?: return@registerForActivityResult
            fillDummyUserImageData(result)
            dummyUser.image?.let { populateImage(it)}
        }

    private fun populateUserInfo() {
        name.text = dummyUser.name
        surname.text = dummyUser.surname
        age.text = dummyUser.age.toString()
//        dummyUser.image?.let { populateImage(it) }
    }
    private fun fillDummyUserTextData(user: User){
        dummyUser = dummyUser.copy(
            name = user.name,
            surname = user.surname,
            age = user.age
        )
    }
    private fun fillDummyUserImageData(uri: Uri){
        dummyUser = dummyUser.copy(
            image = uri
        )
    }
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

//    private val galleryResultContract =
//        registerForActivityResult(ContractGalleryResult()) { result ->
//            result ?: return@registerForActivityResult
//            populateImage(result)
//        }

//        private val cameraResultContract =
//        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
//            if (result != null)
//                imageView.setImageBitmap(result)
//    }
//    private val cameraResultContract =
//        registerForActivityResult(ContractCameraResult()) { result ->
//            result ?: return@registerForActivityResult
////                imageView.setImageBitmap(result)
//            populateImage(result)
//        }
}