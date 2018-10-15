package apps.esampaio.com.comacerto.core.service.preferences

import android.content.Context
import android.support.v7.preference.PreferenceManager
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.fromFormat
import java.util.*

class PreferencesService(val context: Context){

    companion object {

        val PREFERENCE_BREAKFAST_REMINDER_ENABLED_KEY = "PREFERENCE_BREAKFAST_REMINDER_ENABLED"
        val PREFERENCE_LUNCH_REMINDER_ENABLED_KEY = "PREFERENCE_LUNCH_REMINDER_ENABLED"
        val PREFERENCE_SNACK_REMINDER_ENABLED_KEY = "PREFERENCE_SNACK_REMINDER_ENABLED"
        val PREFERENCE_DINNER_REMINDER_ENABLED_KEY = "PREFERENCE_DINNER_REMINDER_ENABLED"

        val PREFERENCE_BREAKFAST_REMINDER_TIME_KEY = "PREFERENCE_BREAKFAST_REMINDER_TIME_VALUE"
        val PREFERENCE_LUNCH_REMINDER_TIME_KEY = "PREFERENCE_LUNCH_REMINDER_TIME_VALUE"
        val PREFERENCE_SNACK_REMINDER_TIME_KEY = "PREFERENCE_SNACK_REMINDER_TIME_VALUE"
        val PREFERENCE_DINNER_REMINDER_TIME_KEY = "PREFERENCE_DINNER_REMINDER_TIME_VALUE"
    }

    fun getPreferenceString(key: String,defaultValue:String):String{
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getString(key,defaultValue)
    }
    fun setPreferenceString(key: String,value:String){
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sp.edit()
        editor.putString(key,value)
        editor.apply()
    }

    public fun getMealTypePreferenceKey(mealType: MealType): String{
        var prefKey = when(mealType){
            MealType.Breakfast -> PREFERENCE_BREAKFAST_REMINDER_TIME_KEY
            MealType.Lunch -> PREFERENCE_LUNCH_REMINDER_TIME_KEY
            MealType.Snack -> PREFERENCE_SNACK_REMINDER_TIME_KEY
            MealType.Dinner -> PREFERENCE_DINNER_REMINDER_TIME_KEY
            else -> {
                ""
            }
        }
        return prefKey
    }

    fun getMealTimeReminder(mealType: MealType):Date?{
        val preferenceKey = getMealTypePreferenceKey(mealType)
        if ( preferenceKey != null) {
            val value = getPreferenceString(preferenceKey,"")
            if( value.isEmpty()){
                return null
            }
            return Date().fromFormat("HH:mm", value)
        }else{
            return null
        }
    }

    fun updateMealTimeValue(mealType: MealType,time:Date){
        val preferenceKey = getMealTypePreferenceKey(mealType)
        val value = time.asString("HH:mm")
        setPreferenceString(preferenceKey,value)

    }

}