package apps.esampaio.com.comacerto.core.service.preferences

import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceManager

fun Preference.update(value : String){
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sp.edit()
    editor.putString(key, value)
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
        editor.apply()
    } else {
        editor.commit()
    }
}