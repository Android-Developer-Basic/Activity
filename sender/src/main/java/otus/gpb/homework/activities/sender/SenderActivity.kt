package otus.gpb.homework.activities.sender

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SenderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        val googleMapsBtn = findViewById<Button>(R.id.btnGoogleMaps)
        googleMapsBtn.setOnClickListener { openGoogleMapsIntent() }

        val sendMailBtn = findViewById<Button>(R.id.btnSendEmail)
        sendMailBtn.setOnClickListener { openEmailIntent() }

        val openReceiverBtn = findViewById<Button>(R.id.btnOpenReceiver)
        openReceiverBtn.setOnClickListener { openReceiverIntent() }

    }

    private fun openGoogleMapsIntent() {
        val predicate = resources.getString(R.string.restaurants)
        val uri = Uri.parse("geo:0, 0?q=$predicate")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun openEmailIntent() {
        val otusEmail = resources.getString(R.string.otus_email)
        val otusEmailMessage = resources.getString(R.string.otus_email_message)
        val uri =
            Uri.parse("mailto:$otusEmail?subject=Activity 02 homework (ProkofevDV)&body=$otusEmailMessage")
        val intent = Intent(Intent.ACTION_SENDTO, uri)

        try {
            startActivity(Intent.createChooser(intent, "Send email"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun openReceiverIntent() {

        var items = parseTxtFile()

        val receiverIntent = Intent(Intent.ACTION_SEND).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            type = "text/plain"
            putExtra("title", items[0].title)
            putExtra("description", items[0].description)
            putExtra("year", items[0].year)
        }

        try {
            startActivity(receiverIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun parseTxtFile(): List<Payload> {
        val path = "payload.txt"
        val payloadList: MutableList<Payload> = mutableListOf()
        try {
            val list: MutableList<String> = mutableListOf()

            application.assets.open(path).bufferedReader().useLines { lines ->
                lines
                    .filter { it.isNotEmpty() }
                    .forEach {
                        list.add(it.substringAfter(": "))
                        if (list.size == 3) {
                            payloadList.add(Payload(list[0], list[1], list[2]))
                            list.clear()
                        }
                    }
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Файл $path не найден", Toast.LENGTH_SHORT).show()

        }
        return payloadList.sortedBy { it.title }
    }

}