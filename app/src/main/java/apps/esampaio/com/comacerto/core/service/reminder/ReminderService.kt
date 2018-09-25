package apps.esampaio.com.comacerto.core.service.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import apps.esampaio.com.comacerto.core.entity.MealType
import java.util.*


class ReminderService {

    fun scheduleReminder(context: Context, mealType: MealType,hour:Date){

        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, MealAlarmBroadcastReceiver::class.java)
        val bundle = Bundle();

        val mealName = mealType.getName(context)
        bundle.putString("MESSAGE","Está quase na hora do seu ${mealName}")
        bundle.putInt("MEAL_TYPE_ORDINAL",mealType.ordinal)
        intent.putExtras(bundle)
        val alarmIntent = PendingIntent.getBroadcast(context, idForMealType(mealType), intent, 0)

        alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                hour.time,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        )
    }

    fun cancelAllMealReminders(context: Context){
        for (mealType in MealType.values()) {
            val intent = Intent(context, MealAlarmBroadcastReceiver::class.java)
            val alarmIntent = PendingIntent.getBroadcast(context, idForMealType(mealType), intent, 0)

            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(alarmIntent)
        }
    }
    private fun idForMealType(mealType: MealType) : Int{
        return mealType.ordinal + 100
    }
}