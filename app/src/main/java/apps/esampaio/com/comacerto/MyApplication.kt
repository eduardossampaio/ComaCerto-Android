package apps.esampaio.com.comacerto

import android.app.Application

class MyApplication : Application() {
    companion object {
        var instance : MyApplication = MyApplication()
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}