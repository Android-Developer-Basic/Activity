package otus.gpb.homework.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import android.provider.Settings
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap

class EditProfileActivity : AppCompatActivity() {
    //Variables:
    private lateinit var imageView: ImageView
    private val userProfile:User = User()
    private var isFirstClickOnImg = true
    private var isFirstAlertDialogCall = true
    private var deny = false
    private lateinit var nameTextView:TextView
    private lateinit var surnameTextView:TextView
    private lateinit var ageTextView:TextView
    private var userPhoto: Uri? = null

    //Contracts:

    //EditProfile <-> FillFromActivity:
    private val userContract = registerForActivityResult(DataContract()){
        if(it == null) Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show()
        else {
            val resultArray = it.split("*_*")
            userProfile.name = resultArray[0]
            userProfile.surname = resultArray[1]
            userProfile.age = resultArray[2]
            setProfileProperties()
        }
    }

    //Images from gallery:
    private val getImgContract = registerForActivityResult(ActivityResultContracts.GetContent()){
        it?.let {
            userPhoto = it
            populateImage(it)
        }
    }

    //Take photo:
    private val camera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        it?.let{
            userProfile.image = it
            imageView.setImageBitmap(it)
            setUri()
        }
    }

    //Permissions to use the camera:
    private val permissionForCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when {
            it -> {
                showChoice()
                deny = true
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

    //Functions:

    //Set user data:
    private fun setProfileProperties(){
        nameTextView.text = userProfile.name
        surnameTextView.text = userProfile.surname
        ageTextView.text = userProfile.age
    }

    //Click on ImageView:
    private fun clickOnTakePhoto(isFirst: Boolean){
        if(deny){
            permissionForCamera.launch(Manifest.permission.CAMERA)
            return
        }
        if(isFirst) permissionForCamera.launch(Manifest.permission.CAMERA)
        else showAlertDialog(isFirstAlertDialogCall)
    }

    //Permissions alert dialog:
    private fun showAlertDialog(isFirst:Boolean = true){
        val alert = AlertDialog.Builder(this)
        if(isFirst) {
            alert.setTitle(resources.getString(R.string.text_for_alert_dialog))
            alert.setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
            }

            alert.setPositiveButton("Access") { _, _ ->
                permissionForCamera.launch(Manifest.permission.CAMERA)
            }
        }
        else alert.setPositiveButton("Settings"){ _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply{
                val uri = Uri.fromParts("package", packageName, null)
                data = uri
            }
            startActivity(intent)
        }
        isFirstAlertDialogCall = false
        alert.show()
    }

    //Choice (take photo, or get default image):
    private fun showChoice(){
        val alert = AlertDialog.Builder(this)
        alert.setPositiveButton("Camera"){_, _ ->
            camera.launch(null)
        }

        alert.setNegativeButton("Cat"){_, _ ->
            val img = getDrawable(R.drawable.cat)
            img?.let{
                userProfile.image = img.toBitmap()
                imageView.setImageBitmap(userProfile.image)
                setUri()
            }
        }
        alert.show()
    }

    //Take photo, or chose photo:
    private fun showDialog(){
        val dialog = Dialog(this)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_window_shape)
        val dialogList = dialog.findViewById<ListView>(R.id.list_view)
        val dialogArray = resources.getStringArray(R.array.dialogWindowArray)
        val dialogAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dialogArray)
        dialogList.adapter = dialogAdapter
        dialogList.onItemClickListener = AdapterView.OnItemClickListener{
            parent, view, position, id ->
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
            if(userPhoto != null) putExtra(Intent.EXTRA_STREAM, userPhoto)
            putExtra(Intent.EXTRA_TEXT, "${userProfile.name}\n${userProfile.surname}\n${userProfile.age}")
        }
        telegramMessage.setPackage("org.telegram.messenger")

        try{
            startActivity(telegramMessage)
        }
        catch (e: ActivityNotFoundException){
            Toast.makeText(this, "Telegram is not installed", Toast.LENGTH_SHORT).show()
        }
    }

    //Set uri for image if was clicked on "Take photo":
    private fun setUri(){
        val path = Media.insertImage(application.contentResolver,userProfile.image,"user_photo", null)
        userPhoto = Uri.parse(path)
    }

}