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
import com.andreirookie.app.util.ContractCameraResult
import com.andreirookie.app.util.ContractEditProfileAndFillForm
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.activities.R

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editProfileButton: Button

    private lateinit var name: TextView
    private lateinit var surname: TextView
    private lateinit var age: TextView

    private val dummyUser = User(
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
            val user = User(
                name = name.text.toString(),
                surname = surname.text.toString(),
                age = age.text.toString().toInt()
            )
            fillFormLauncher.launch(user)
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

        val intent = Intent(Intent.ACTION_VIEW)
            .setPackage("org.telegram.messenger")
            .setType("*/*")
//            .putExtra("image", imageView.setImageURI())

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            println(e.message)
        }
    }

    private val fillFormLauncher =
        registerForActivityResult(ContractEditProfileAndFillForm()) { user ->
            user ?: return@registerForActivityResult
            populateUserInfo(user)
        }

    private fun populateUserInfo(user: User) {
        name.text = user.name
        surname.text = user.surname
        age.text = user.age.toString()
    }

    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
//                    imageView.setImageDrawable(getDrawable(R.drawable.cat))
                    populateImage(Uri.parse("android.resource://$packageName/${R.drawable.cat}"))
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
            cameraPermissionRequest.launch(Manifest.permission.CAMERA)
        }
    }

    private fun grantCameraAccessViaAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Warning")
            .setMessage("Для использования камеры необходимо получить разрешение")
            .setPositiveButton("Дать доступ") { _, _ ->
                cameraPermissionRequest.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Отмена") { text, _ ->
//                text.dismiss() / cancel
            }.show()
    }

    private val galleryResultContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result ?: return@registerForActivityResult
            populateImage(result)
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