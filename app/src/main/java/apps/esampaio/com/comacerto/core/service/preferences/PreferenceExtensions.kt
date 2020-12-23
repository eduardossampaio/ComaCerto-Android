package apps.esampaio.com.comacerto.core.service.preferences

import androidx.preference.Preference
import androidx.preference.PreferenceManager

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