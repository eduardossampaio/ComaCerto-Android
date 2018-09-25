package apps.esampaio.com.comacerto.core.service.preferences

import android.content.Context
import android.support.v7.preference.PreferenceManager
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.extensions.fromFormat
import java.util.*

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

    fun getMealTimeReminder(mealType: MealType):Date{
        var value = when(mealType){
            MealType.Breakfast -> getPreferenceString(PREFERENCE_BREAKFAST_REMINDER_TIME_KEY,"")
            MealType.Lunch -> getPreferenceString(PREFERENCE_LUNCH_REMINDER_TIME_KEY,"")
            MealType.Snack -> getPreferenceString(PREFERENCE_SNACK_REMINDER_TIME_KEY,"")
            MealType.Dinner -> getPreferenceString(PREFERENCE_DINNER_REMINDER_TIME_KEY,"")
            else -> {
                ""
            }
        }
        return Date().fromFormat("HH:mm",value)
    }

}