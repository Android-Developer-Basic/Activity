package otus.gpb.homework.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Images.Media.insertImage
import android.provider.Settings
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.File
import java.io.IOException
import java.util.*

//Глобальные переменные
internal var agePostfix = ""
internal const val DATA_STRING_SEPARATOR = "*_*"
internal const val TELEGRAM_PACKAGE = "org.telegram.messenger"
internal const val IMAGE_PACKAGE = "otus.gpb.homework.activities.fileprovider"
internal var userPhotoUri: Uri? = null
internal var imgFile:File? = null

class EditProfileActivity : AppCompatActivity() {
    //Переменнные класса
    private lateinit var imageView: ImageView
    private val userProfile:User = User()
    private var isFirstClickOnImg = true
    private var isFirstAlertDialogCall = true
    private var isCameraAccess = false
    private lateinit var nameTextView:TextView
    private lateinit var surnameTextView:TextView
    private lateinit var ageTextView:TextView

    //Контракты:

    //EditProfile <-> FillFromActivity:
    private val userContract = registerForActivityResult(DataContract()){
        if(it == null) {}
        else {
            val resultArray = it.split(DATA_STRING_SEPARATOR)
            userProfile.name = resultArray[0]
            userProfile.surname = resultArray[1]
            userProfile.age = resultArray[2]
            setProfileProperties()
        }
    }

    //Получение изображений из галереи:
    private val getImgContract = registerForActivityResult(ActivityResultContracts.GetContent()){
        it?.let {
            userPhotoUri = it
            populateImage(it)
        }
    }

    //Камера:
    private val camera = registerForActivityResult(CameraContract()){
            populateImage(Uri.fromFile(imgFile))

    }

    //Разрешение на использование камеры:
    private val permissionForCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when {
            it -> {
                showChoice()
                isCameraAccess = true
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package", packageName, null)
                    data = uri
                }
                startActivity(intent)
            }
            else -> isFirstClickOnImg = false
        }
    }
    //OnCreate fun:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener { showDialog() }
        nameTextView = findViewById(R.id.textview_name)
        surnameTextView = findViewById(R.id.textview_surname)
        ageTextView = findViewById(R.id.textview_age)
        val editProfileButton = findViewById<Button>(R.id.button4)
        editProfileButton.setOnClickListener { userContract.launch(userProfile) }

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

    //Функции:

    //Заполнение данных профиля:
    private fun setProfileProperties(){
        nameTextView.text = userProfile.name
        surnameTextView.text = userProfile.surname
        ageTextView.text = userProfile.age + agePostfix
    }

    private fun createImgFile(): File?{
        val dateStamp = java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try{
            File.createTempFile("IMG_${dateStamp}_", ".jpg", storage)

        }catch (e:IOException){
            null
        }
    }

    //Клик по imageView:
    private fun clickOnTakePhoto(isFirst: Boolean){
        if(isCameraAccess){
            permissionForCamera.launch(Manifest.permission.CAMERA)
            return
        }
        if(isFirst) permissionForCamera.launch(Manifest.permission.CAMERA)
        else showAlertDialog(isFirstAlertDialogCall)
    }

    //Алерт-диалог, вызываемый при запросе разрешения на использование камеры:
    private fun showAlertDialog(isFirst:Boolean = true){
        val alert = AlertDialog.Builder(this)
        if(isFirst) {
            alert.setTitle(R.string.alert)
            alert.setMessage(R.string.text_for_alert_dialog)
            alert.setNegativeButton(R.string.deny) { dialog, _ ->
                dialog.dismiss()
            }

            alert.setPositiveButton(R.string.access) { _, _ ->
                permissionForCamera.launch(Manifest.permission.CAMERA)
            }
        }
        else alert.setPositiveButton(R.string.settings){ _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply{
                val uri = Uri.fromParts("package", packageName, null)
                data = uri
            }
            startActivity(intent)
        }
        isFirstAlertDialogCall = false
        alert.show()
    }

    //Дополнительный алерт-диалог, в котором можно выбрать: открыть камеру, либо установить дефолтное изображение:
    private fun showChoice(){
        val alert = AlertDialog.Builder(this)

        //Открыть камеру
        alert.setPositiveButton(R.string.Camera){_, _ ->
            imgFile = createImgFile()
            if(imgFile == null) {}
            else camera.launch(null)
        }

        //Поставить котейку на аватар
        alert.setNegativeButton(R.string.cat){_, _ ->
            userPhotoUri = Uri.parse("android.resource://${this.packageName}/${R.drawable.cat}")
            userPhotoUri?.let {
                populateImage(it)
                @Suppress("DEPRECATION")
                val path = insertImage(application.contentResolver, userProfile.image, "Cat", null)
                userPhotoUri = Uri.parse(path)
            }


        }
        alert.show()
    }

    //Кастомный диалог, вызываемый при клике по imageView:
    private fun showDialog(){
        val dialog = Dialog(this)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_window_shape)
        val dialogList = dialog.findViewById<ListView>(R.id.list_view)
        val dialogArray = resources.getStringArray(R.array.dialogWindowArray)
        val dialogAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dialogArray)
        dialogList.adapter = dialogAdapter
        dialogList.onItemClickListener = AdapterView.OnItemClickListener{
            parent, _, position, _ ->
            val selectedText = parent.getItemAtPosition(position)
            when(selectedText.toString()){
                resources.getString(R.string.take_photo) -> {
                    clickOnTakePhoto(isFirstClickOnImg)
                    dialog.dismiss()
                }
                resources.getString(R.string.chose_photo) -> {
                    getImgContract.launch("image/*")
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        userProfile.image = bitmap
        imageView.setImageBitmap(bitmap)
    }
    private fun openSenderApp() {

        val telegramMessage = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            if(userPhotoUri != null) putExtra(Intent.EXTRA_STREAM, userPhotoUri )
            putExtra(Intent.EXTRA_TEXT, "${userProfile.name}\n${userProfile.surname}\n${userProfile.age+ agePostfix}")
        }
        telegramMessage.setPackage(TELEGRAM_PACKAGE)

        try{
            startActivity(telegramMessage)
            //startActivity(Intent.createChooser(telegramMessage,""))
        }
        catch (e: ActivityNotFoundException){
            Toast.makeText(this, R.string.app_not_installed, Toast.LENGTH_SHORT).show()
        }
    }

}