package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
//import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
//import kotlin.jvm.java

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var nameView: TextView
    private lateinit var surnameView: TextView
    private lateinit var ageView: TextView

    private val pickImageContract = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            populateImage(uri)
        } else {
            Toast.makeText(this, "Фото не выбрано", Toast.LENGTH_SHORT).show()
        }
    }

    private val fillFormLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val name = result.data?.getStringExtra("name") ?: ""
            val surname = result.data?.getStringExtra("surname") ?: ""
            val age = result.data?.getStringExtra("age") ?: ""

            nameView.text = name
            surnameView.text = surname
            ageView.text = age

            Toast.makeText(this, "Профиль обновлён", Toast.LENGTH_SHORT).show()
        }
    }

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imageView = findViewById(R.id.imageview_photo)
        nameView = findViewById(R.id.textview_name)
        surnameView = findViewById(R.id.textview_surname)
        ageView = findViewById(R.id.textview_age)

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

        findViewById<Button>(R.id.button_edit_profile).setOnClickListener {
            openFillForm()
        }

        imageView.setOnClickListener {
            showActionDialog()
        }
    }

    private fun showActionDialog() {
        val options = arrayOf("Сделать фото", "Выбрать фото")
        AlertDialog.Builder(this)
        .setTitle("Выберите действие")
        .setItems(options) { _, which ->
            when (which) {
                0 -> requestCameraPermission()
                1 -> selectPhotoFromGallery()
            }
        }
        .show()
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Сценарий 1: Разрешение есть -> ставим кота (или делаем фото)
            setDefaultImage()
        } else {
            // Проверяем, показывал ли пользователь "Не спрашивать снова"
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // Сценарий 3: Пользователь ранее отменил. Показываем Rationale.
                showRationaleDialog()
            } else {
                // Сценарий 2: Первый раз запрашиваем разрешение
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Необходима камера")
            .setMessage("Чтобы обновить фото профиля, приложению нужно разрешение на использование камеры.")
            .setPositiveButton("Дать доступ") { _, _ ->
                // Повторно запрашиваем разрешение
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
            .setNegativeButton("Отмена") { _, _ ->
                // Ничего не делаем, диалог просто закрываем
            }
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Сценарий 1 (после повторного запроса): пользователь дал доступ
            setDefaultImage()
        } else if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            // Сценарий 4: Пользователь повторно запретил
            showFinalDeniedDialog()
        }
    }

    private fun showFinalDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Доступ к камере заблокирован")
            .setMessage("Вы дважды отказали в доступе. Чтобы включить его, зайдите в настройки приложения.")
            .setPositiveButton("Открыть настройки") { _, _ ->
                val intent = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = android.net.Uri.fromParts("package", packageName, null)
                val settingsIntent = Intent(intent, uri)
                startActivity(settingsIntent)
            }
            .setCancelable(false) // Нельзя просто так закрыть
            .show()
    }

    private fun setDefaultImage() {
        imageView.setImageResource(R.drawable.cat)
        Toast.makeText(this, "Фото обновлено", Toast.LENGTH_SHORT).show()
    }

    private fun selectPhotoFromGallery() {
//        реализация без контракта
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, 200)
//        реализация через контракт
        // 2. Запускаем контракт. Тип "image/*" гарантирует, что откроется галерея/пикер картинок
        pickImageContract.launch("image/*")
    }
// реализация без контракта, ловим код возврата
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
//            val uri: Uri = data.data!!
//            // Тут можно вызвать populateImage(uri), если нужно реальное фото
//            Toast.makeText(this, "Выбрано фото из галереи", Toast.LENGTH_SHORT).show()
//        }
//    }

    // Метод для открытия FillFormActivity
    private fun openFillForm() {
        val intent = Intent(this, FillFormActivity::class.java)
        fillFormLauncher.launch(intent)
    }
    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        currentImageUri = uri  // Сохраняем URI выбранного фото

        try {
            // оборачиваем в try-catch, так как доступ к контенту может упасть, при невалидном URI
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                Toast.makeText(this, "Фото обновлено", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка при чтении файла", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSenderApp() {
        // Собираем данные из полей
        val name = nameView.text.toString()
        val surname = surnameView.text.toString()
        val age = ageView.text.toString()
//        // Задание 1 Создаём Intent и кладём туда:
//        // ID картинки,
//        // для примера берем текущую картинку из drawable
//        val intent = Intent(Intent.ACTION_SEND).apply {
//            type = "text/plain"
//            putExtra("name", name)
//            putExtra("surname", surname)
//            putExtra("age", age)
//            putExtra("image_res_id", R.drawable.cat)
//        }
//        Задание 3
//    Telegram не умеет принимать картинку как res_id. Чтобы отправить фото, нужно:
//    1. Иметь реальный Uri картинки (из галереи).
//    2. Передать его через Intent.EXTRA_STREAM.
//    3. Указать MIME-тип.
//    Поэтому в EditProfileActivity нужно хранить текущий Uri выбранного фото.
        // Формируем текст сообщения
        val message = "Имя: $name\nФамилия: $surname\nВозраст: $age"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"  // Важно: тип для отправки картинки
            putExtra(Intent.EXTRA_TEXT, message)

            // Если есть URI фото — добавляем его
            currentImageUri?.let { uri ->
                putExtra(Intent.EXTRA_STREAM, uri)
            }
        }

        // Пробуем открыть Telegram. Если Telegram не установлен, Intent.createChooser покажет список всех приложений, способных принять картинку и текст
        try {
            startActivity(Intent.createChooser(intent, "Отправить через"))
        } catch (e: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "Нет приложений для отправки {$e}", Toast.LENGTH_SHORT).show()
        }

        startActivity(intent)
    }
}