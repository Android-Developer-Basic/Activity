package otus.gpb.homework.activities

import android.app.Application
import android.util.Log
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "App onCreate")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.i("TAG", "App onTerminate")
    }
}