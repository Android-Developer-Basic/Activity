package otus.gpb.homework.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Images.Media.insertImage
import android.provider.Settings
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException
import java.util.*

//Глобальные переменные
internal var agePostfix = ""
internal const val DATA_STRING_SEPARATOR = "*_*"
internal const val TELEGRAM_PACKAGE = "org.telegram.messenger"
internal const val IMAGE_PACKAGE = "otus.gpb.homework.activities.fileprovider"

class EditProfileActivity : AppCompatActivity() {
    //Переменнные класса
    private lateinit var imageView: ImageView
    private val userProfile:User = User()
    private lateinit var nameTextView:TextView
    private lateinit var surnameTextView:TextView
    private lateinit var ageTextView:TextView
    private val cameraP = Manifest.permission.CAMERA
    private val storageP = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private var userPhotoUri: Uri? = null
    private var imgFile:File? = null
    private var catImagePath = ""

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

    //Контракты:

    //EditProfile <-> FillFromActivity:
    private val userContract = registerForActivityResult(DataContract()){
        it?.let {
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
    private val camera = registerForActivityResult(ActivityResultContracts.TakePicture()){ result->
        if(result) userPhotoUri?.let { imageView.setImageURI(it) }

    }
    // Разрешение на получение Uri котейки, если  версия SDK - 28 и меньше"
    private  val writeStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        when{
            it -> setCat()
            !shouldShowRequestPermissionRationale(storageP) -> showAlertDialog(false, permission = storageP)
        }
    }
    // Разрешение на доступ к камере
    private val cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        when{
            it -> showChoice()
            !shouldShowRequestPermissionRationale(cameraP) -> showAlertDialog(false, permission = cameraP)
        }
    }

    //Проверка разрешений:
   private fun checkPermission(permission:String){
       when{
           ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED ->{
               when(permission){
                   cameraP -> showChoice()
                   storageP -> setCat()
               }
           }
           shouldShowRequestPermissionRationale(permission) ->{
               when(permission){
                   cameraP -> showAlertDialog(permission = permission)
                   storageP -> showAlertDialog(permission = permission)
               }
           }

           else -> {
               when(permission){
                   cameraP -> cameraPermission.launch(permission)
                   storageP -> writeStoragePermission.launch(permission)
               }

           }
       }
   }

    //Функции:

    //Заполнение данных профиля:
    @SuppressLint("SetTextI18n")
    private fun setProfileProperties(){
        nameTextView.text = userProfile.name
        surnameTextView.text = userProfile.surname
        ageTextView.text = userProfile.age + agePostfix
    }

    //Файл для хранения фото с камеры
    @SuppressLint("SimpleDateFormat")
    private fun createImgFile(): File?{
        val dateStamp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        } else {
            java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        }
        val storage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try{
            File.createTempFile("IMG_${dateStamp}_", ".jpg", storage)

        }catch (e:IOException){
            null
        }
    }

    //Клик по imageView:
    private fun clickOnTakePhoto(){
        checkPermission(cameraP)

    }

    //Алерт-диалог, вызываемый при запросе разрешения:
    private fun showAlertDialog(isFirstShowDialog: Boolean = true, permission:String){

            val alert = MaterialAlertDialogBuilder(this)
            var alertMessage = -1
            when (permission) {
                cameraP -> {
                    alertMessage = R.string.text_for_alert_dialog
                }
                storageP -> {
                    alertMessage = R.string.text_for_alert_dialog2
                }
            }
            if (!isFirstShowDialog) alert.setPositiveButton(R.string.settings) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    val uri = Uri.fromParts("package", packageName, null)
                    data = uri
                }
                startActivity(intent)

            }
            else {
                alert.setTitle(R.string.alert)
                alert.setMessage(alertMessage)
                alert.setNegativeButton(R.string.deny) { dialog, _ ->

                }

                alert.setPositiveButton(R.string.access) { _, _ ->
                    when (permission) {
                        cameraP -> cameraPermission.launch(permission)
                        storageP -> writeStoragePermission.launch(permission)
                    }
                }

            }
            alert.show()

    }

    //Дополнительный алерт-диалог, в котором можно выбрать: открыть камеру, либо установить дефолтное изображение:
    private fun showChoice(){
        val alert = AlertDialog.Builder(this)

        //Открыть камеру
        alert.setPositiveButton(R.string.Camera){_, _ ->
            imgFile = createImgFile()
            imgFile?.let{
                userPhotoUri = FileProvider.getUriForFile(this, IMAGE_PACKAGE, it)
                camera.launch(userPhotoUri)
            }

        }

        //Поставить котейку на аватар
        alert.setNegativeButton(R.string.cat){_, _ ->
            if(Build.VERSION.SDK_INT <= 28) checkPermission(storageP)
            else setCat()

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
                    clickOnTakePhoto()
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

    private fun setCat(){
        userPhotoUri = Uri.parse("android.resource://${this.packageName}/${R.drawable.cat}")
        userPhotoUri?.let {
            populateImage(it)
            @Suppress("DEPRECATION")
            catImagePath = insertImage(application.contentResolver, userProfile.image, "Cat", null)
            userPhotoUri = Uri.parse(catImagePath)
        }
    }

}
