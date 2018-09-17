package apps.esampaio.com.comacerto

import android.app.Application
import apps.esampaio.com.comacerto.core.entity.Feeling
import apps.esampaio.com.comacerto.core.entity.MealType

class MyApplication : Application() {
    companion object {
        var instance : MyApplication = MyApplication()
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        //to load images
        Feeling.values()
        MealType.values()
    }
}