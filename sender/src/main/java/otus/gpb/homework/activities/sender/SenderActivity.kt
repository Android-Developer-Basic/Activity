package otus.gpb.homework.activities.sender


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader



class SenderActivity : AppCompatActivity() {
    private lateinit var payloadList:List<Payload>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)

        //EditText:
        val searchInGM = findViewById<EditText>(R.id.search_in_gm)
        searchInGM.setText("Рестораны")
        val addressET = findViewById<EditText>(R.id.edit_address)
        addressET.setText("android@otus.ru")
        val messageET = findViewById<EditText>(R.id.edit_message)

        //Spinner:
        val receiverSpinner = findViewById<Spinner>(R.id.receiver_spinner)

        //Buttons:

        val googleMapsBtn = findViewById<Button>(R.id.google_maps_button)
        googleMapsBtn.setOnClickListener { toGoogleMaps(searchInGM) }
        val sendMailBtn = findViewById<Button>(R.id.send_mail_button)
        sendMailBtn.setOnClickListener { sendMail(addressET,messageET) }
        val openReceiverBtn = findViewById<Button>(R.id.open_receiver_button)
        openReceiverBtn.setOnClickListener { openReceiver(receiverSpinner) }
        fillSpinnerItems(receiverSpinner)
    }


    //Functions:

    private fun toGoogleMaps(editText: EditText){
        val searchQuery = editText.text.toString()
        val uriGM = Uri.parse("geo:0, 0?q=$searchQuery")
        val gMapsIntent = Intent(Intent.ACTION_VIEW, uriGM)
        gMapsIntent.setPackage("com.google.android.apps.maps")
        startActivity(gMapsIntent)

    }

    private fun sendMail(addressET: EditText, messageET: EditText){
        val address = addressET.text.toString()
        if(address == ""){
            Toast.makeText(this, "Укажите адрес!", Toast.LENGTH_SHORT).show()
            return
        }
        val message = messageET.text.toString()
        val uri = Uri.parse("mailto:$address?subject=Homework: Activity-2&body=$message")
        val postIntent = Intent(Intent.ACTION_SENDTO, uri)

        try {
            startActivity(Intent.createChooser(postIntent, "Send email"))
        }
        catch (e: ActivityNotFoundException){
            Toast.makeText(this, "Приложение не найдено.", Toast.LENGTH_SHORT).show()
            return
        }

    }

    private fun openReceiver(spinner: Spinner){
        val item = spinner.selectedItem
        var payload:Payload? = null
        payloadList.map{if(it.title == item) payload = it; return@map}
        if(payload != null) {
            val receiverIntent = Intent("Action.SEND").apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                type = "text/plain"
                putExtra("T", payload?.title)
                putExtra("D", payload?.description)
                putExtra("Y", payload?.year)
                putExtra("tag", payload?.tag)
            }
            try {
                startActivity(receiverIntent)
            }
            catch (e: ActivityNotFoundException){
                Toast.makeText(this, "Приложение не найдено.", Toast.LENGTH_SHORT).show()
                return
            }

        }
        else{
            Toast.makeText(this, "Похоже, Вы пытаетесь отправить пустой запрос.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun parseTxtFile(): List<Payload> {
        val path = "payload.txt"
        val txtFile: BufferedReader
        val payloadList: MutableList<Payload> = mutableListOf()
        try {
            txtFile = BufferedReader(InputStreamReader(assets.open(path)))
            while (true) {
                val lineArray = mutableListOf<String>()
                for (i in 0 until 4) {
                    try {
                        lineArray.add(txtFile.readLine().substringAfter(": "))
                    } catch (e: Exception) {
                        break
                    }
                }
                if (lineArray.size == 4) {
                    val newPayLoad = Payload(lineArray[0], lineArray[1], lineArray[2], lineArray[3])
                    payloadList.add(newPayLoad)
                } else break
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Файл $path не найден", Toast.LENGTH_SHORT).show()

        }
        return payloadList.sortedBy { it.title }
    }

    private fun fillSpinnerItems(spinner: Spinner){
        payloadList = parseTxtFile()
        val list = payloadList.map { it.title }
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, list)
        spinner.adapter = adapter
    }
}