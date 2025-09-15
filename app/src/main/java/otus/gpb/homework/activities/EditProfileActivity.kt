package otus.gpb.homework.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import otus.gpb.homework.contracts.ContractForFillFormActivity
import otus.gpb.homework.model.User
import androidx.core.net.toUri

/**
 * Полезная статья по разрешениям [shouldShowRequestPermissionRationale] и
 * Result API: [Habr Article](https://habr.com/ru/companies/e-legion/articles/545934/)
 */
class EditProfileActivity : AppCompatActivity() {

    //region elementsById
    private val chooseImageDialogItems by lazy {
        arrayOf(
            resources.getString(R.string.choose_dialog_create_photo),
            resources.getString(R.string.choose_dialog_choose_photo)
        )
    }

    private val imageView by lazy { findViewById<ImageView>(R.id.imageview_photo) }

    private val editProfileButton by lazy { findViewById<Button>(R.id.edit_profile_button) }

    private val firstName by lazy { findViewById<TextView>(R.id.textview_first_name) }
    private val lastName by lazy { findViewById<TextView>(R.id.textview_last_name) }
    private val age by lazy { findViewById<TextView>(R.id.textview_age) }

    //endregion
    private var currentItemsWhichChooseImageDialog = -1

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView.setOnClickListener { chooseImageDialog.show() }
        editProfileButton.setOnClickListener {
            openFillProfileIntent()
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

    //region activity results
    private val permissionCameraFromChooseImageDialog = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::handleCameraPermissionFromChooseImageDialog
    )

    private val permissionCameraAfterSettingsActivity = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::handleCameraPermissionAfterSettingsActivity
    )

    private val takePictureUri = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ::handleTakePictureUri
    )

    private val takePictures = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview(),
        ::handleTakePictures
    )

    private val launcherSettings = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ::handleLaunchSettings
    )

    private val fillProfileActivity = registerForActivityResult(
        ContractForFillFormActivity(),
        ::handleFillProfileActivity
    )
    //endregion

    //region dialogs
    val chooseImageDialog: AlertDialog
        get() {
            Log.d("debug_granted", "chooseImageDialog getter.")
            return MaterialAlertDialogBuilder(this)
                .setTitle(R.string.choose_dialog_title)
                .setSingleChoiceItems(chooseImageDialogItems, -1) { _, which ->
                    currentItemsWhichChooseImageDialog = which
                }
                .setPositiveButton(
                    R.string.choose_dialog_ok,
                    ::onPositiveChooseImageDialogListenerClick
                )
                .setNegativeButton(R.string.choose_dialog_canceled) { dialog, _ ->
                    Log.d("debug_granted", "chooseImageDialog onNegativeButtonClickListener.")
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    Log.d("debug_granted", "chooseImageDialog onDismissListener.")
                    currentItemsWhichChooseImageDialog = -1
                }
                .create()
        }

    val rationaleDialog: AlertDialog
        get() {
            Log.d("debug_granted", "rationaleDialog getter.")
            return MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.rationale_dialog_title))
                .setMessage(getString(R.string.rationale_dialog_message))
                .setPositiveButton(
                    R.string.rationale_dialog_button_ok,
                    ::rationaleDialogInClickListener
                )
                .setNegativeButton(R.string.rationale_dialog_button_canceled) { _, _ ->
                    Log.d("debug_granted", "rationaleDialog onNegativeButtonClickListener.")
                }
                .setOnDismissListener {
                    Log.d("debug_granted", "rationaleDialog onDismissListener.")
                }
                .create()
        }

    val requirementDialog: AlertDialog
        get() {
            Log.d("debug_granted", "requirementDialog getter.")
            return MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.rationale_dialog_title))
                .setMessage(getString(R.string.rationale_dialog_message))
                .setPositiveButton(
                    R.string.rationale_dialog_button_ok,
                    ::requirementDialogInClickListener
                )
                .setOnDismissListener {
                    Log.d("debug_granted", "requirementDialog onDismissListener.")
                }
                .create()
        }
    //endregion

    //region handlers
    private fun handleCameraPermissionFromChooseImageDialog(granted: Boolean) {
        when {
            granted -> {
                setCameraImage()
                Log.d("debug_granted", "handleCameraPermissionFromChooseImageDialog: granted")
                Toast.makeText(this, "Доступ к камере предоставлен", Toast.LENGTH_SHORT).show()
            }
            // доступ к камере запрещен, пользователь поставил галочку Don't ask again.
            isCameraForbidden() -> {
                Log.d("debug_granted", "camera forbidden")
                rationaleDialog.show()
            }
            // доступ к камере запрещен, пользователь отклонил запрос
            else -> {
                Log.d("debug_granted", "camera denied")
                Toast.makeText(this, "Потом попробуешь еще раз", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleCameraPermissionAfterSettingsActivity(granted: Boolean) {
        Log.d("debug_granted", "handleCameraPermissionAfterSettingsActivity: granted: $granted")
        if (!granted) requirementDialog.show()
        else setCameraImage()
    }


    private fun handleLaunchSettings(activityResult: ActivityResult) {
        Log.d("debug_granted", "Callback launcherSettings.")
        permissionCameraAfterSettingsActivity.launch(Manifest.permission.CAMERA)
    }

    private fun handleTakePictures(image: Bitmap?) {
        Log.d("debug_granted", "Callback takePictures. image: $image")
        if (image != null) imageView.setImageBitmap(image)
        else imageView.setImageDrawable(getDrawableById(R.drawable.cat))
    }

    private fun handleTakePictureUri(image: Uri?) {
        Log.d("debug_granted", "Callback takePictureUri. uri: $image")
        image?.let {
            this.imageUri = image
            populateImage(image)
        }
    }

    fun handleFillProfileActivity(user: User?) {
        Log.d("debug_granted", "fillProfileActivity result: $user")
        user?.let {
            firstName.text = user.firstName
            lastName.text = user.lastName
            age.text = user.age.toString()
        }
    }
    //endregion

    //region listeners
    private fun rationaleDialogInClickListener(dialog: DialogInterface?, which: Int) {
        Log.d("debug_granted", "rationaleDialogInClickListener")
        startSettingsActivity()
        dialog?.dismiss()
    }

    private fun requirementDialogInClickListener(dialog: DialogInterface?, which: Int) {
        Log.d("debug_granted", "requirementDialogInClickListener")
        startSettingsActivity()
        dialog?.dismiss()
    }

    fun onPositiveChooseImageDialogListenerClick(dialog: DialogInterface?, which: Int) {
        Log.d("debug_granted", "onPositiveChooseImageDialogListenerClick")
        when (currentItemsWhichChooseImageDialog) {
            0 -> permissionCameraFromChooseImageDialog.launch(Manifest.permission.CAMERA)
            1 -> takePictureUri.launch("image/*")
        }
        dialog?.dismiss()
    }

    fun openFillProfileIntent() = Intent(this, FillFormActivity::class.java).apply {
        val ageValue = age.text.toString()
        fillProfileActivity.launch(
            User(
                firstName.text.toString(),
                lastName.text.toString(),
                if (ageValue == "") 0 else runCatching { ageValue.toInt() }.getOrDefault(0)
            )
        )
    }
    //endregion

    // region image settings
    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
    }

    private fun setCameraImage() {
        takePictures.launch()
    }

    /**
     * Получает ресурс drawable по его идентификатору.
     *
     * @param imageId идентификатор ресурса drawable для получения
     * @return ресурс drawable, связанный с данным идентификатором, или null, если не найден
     */
    private fun getDrawableById(imageId: Int) =
        AppCompatResources.getDrawable(this, imageId)
    //endregion

    private fun openSenderApp() {
        val telegramIntent = Intent(Intent.ACTION_SEND).apply {
            setPackage("org.telegram.messenger")
            setType("text/plain")
            val message = """
                ${firstName.text} 
                ${lastName.text}
                ${age.text}
            """.trimIndent()
            if (message.isBlank()) {
                Toast.makeText(
                    this@EditProfileActivity,
                    "Сначала введите свои данные",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            putExtra(
                Intent.EXTRA_TEXT,
                message
            )
            imageUri?.let {
                putExtra(Intent.EXTRA_STREAM, it)
            }

        }

        runCatching {
            startActivity(telegramIntent)
            Log.d("debug_granted", "openSenderApp: success")
        }.getOrElse {
            Toast.makeText(this, "Похоже Telegram не установлен", Toast.LENGTH_SHORT).show();
            Log.e("debug_granted", "openSenderApp failed to start Telegram : ${it.message}", it)
        }
    }

    private fun isCameraForbidden(): Boolean =
        !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)

    fun startSettingsActivity() {
        Log.d("debug_granted", "startSettingsActivity")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        launcherSettings.launch(intent)
    }
}
