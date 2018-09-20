package apps.esampaio.com.comacerto.core.service.preferences

import android.content.Context
import android.support.v7.preference.PreferenceManager

class PreferencesService(val context: Context){

    companion object {
        val PREFERENCE_ENABLE_REMINDERS_KEY = "PREFERENCE_ENABLE_REMINDER"
        val PREFERENCE_BREAKFAST_REMINDER_TIME_KEY = "PREFERENCE_BREAKFAST_REMINDER_TIME"
        val PREFERENCE_LUNCH_REMINDER_TIME_KEY = "PREFERENCE_LUNCH_REMINDER_TIME"
        val PREFERENCE_SNACK_REMINDER_TIME_KEY = "PREFERENCE_SNACK_REMINDER_TIME"
        val PREFERENCE_DINNER_REMINDER_TIME_KEY = "PREFERENCE_DINNER_REMINDER_TIME"
    }

    fun getPreferenceString(key: String,defaultValue:String):String{
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getString(key,defaultValue)
    }

}