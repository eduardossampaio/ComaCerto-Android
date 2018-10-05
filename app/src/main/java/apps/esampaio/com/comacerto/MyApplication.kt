package apps.esampaio.com.comacerto

import android.app.Application
import apps.esampaio.com.comacerto.core.entity.Feeling
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.firebase.RemoteConfig
import st.lowlevel.storo.StoroBuilder
import android.support.v4.os.ConfigurationCompat.getLocales
import android.os.Build
import java.util.*


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

        //fetch remote config
        RemoteConfig.getInstance()

        StoroBuilder.configure(8192000)  // maximum size to allocate in bytes
                .setDefaultCacheDirectory(this)
                .initialize();
    }

    fun getCurrentLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }
    }
}