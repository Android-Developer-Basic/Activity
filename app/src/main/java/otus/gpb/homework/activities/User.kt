package otus.gpb.homework.activities

import android.graphics.Bitmap

data class User(
    var name:String = "",
    var surname:String = "",
    var age:String = "",
    var image: Bitmap? = null
)
