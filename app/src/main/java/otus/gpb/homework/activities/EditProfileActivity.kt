package otus.gpb.homework.activities

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var isFirstClickOnImg = true
    private var isFirstAlertDialogCall = true
    private var deny = false
    private val getImgContract = registerForActivityResult(ActivityResultContracts.GetContent()){
        it?.let {
            populateImage(it)
        }
    }
    private val camera = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        imageView.setImageBitmap(it)
    }



    private val permissionForCamera = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        when {
            it -> {
                //camera.launch(null)
                val img = getDrawable(R.drawable.cat)
                img?.let{
                    imageView.setImageDrawable(img)
                }
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

    private fun clickOnTakePhoto(isFirst: Boolean){
        if(deny){
            permissionForCamera.launch(Manifest.permission.CAMERA)
            return
        }
        if(isFirst) permissionForCamera.launch(Manifest.permission.CAMERA)
        else showAlertDialog(isFirstAlertDialogCall)
    }


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener { showDialog() }

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
    private fun showAlertDialog(isFirst:Boolean = true){
        val alert = AlertDialog.Builder(this)
        if(isFirst) {
            alert.setTitle(resources.getString(R.string.textForAlertDialog))
            alert.setNegativeButton("Deny") { dialog, index ->
                dialog.dismiss()
            }

            alert.setPositiveButton("Access") { dialog, index ->
                permissionForCamera.launch(Manifest.permission.CAMERA)
            }
        }
        else alert.setPositiveButton("Settings"){ dialog, index ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply{
                val uri = Uri.fromParts("package", packageName, null)
                data = uri
            }
            startActivity(intent)
        }
        isFirstAlertDialogCall = false
        alert.show()
    }
    private fun showDialog(){
        val dialog = Dialog(this)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_window_shape)
        val dialogList = dialog.findViewById<ListView>(R.id.listView)
        val dialogArray = resources.getStringArray(R.array.dialogWindowArray)
        val dialogAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dialogArray)
        dialogList.adapter = dialogAdapter
        dialogList.onItemClickListener = AdapterView.OnItemClickListener{
            parent, view, position, id ->
            val selectedText = parent.getItemAtPosition(position)
            when(selectedText.toString()){
                resources.getString(R.string.takePhoto) -> {
                    clickOnTakePhoto(isFirstClickOnImg)
                    dialog.dismiss()
                }
                resources.getString(R.string.chosePhoto) -> {
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
        imageView.setImageBitmap(bitmap)
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }

}