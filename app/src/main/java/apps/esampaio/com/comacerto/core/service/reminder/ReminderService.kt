package apps.esampaio.com.comacerto.core.service.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import apps.esampaio.com.comacerto.core.entity.MealType
import java.util.*


class ReminderService {

    fun scheduleReminder(context: Context, mealType: MealType,hour:Date){
        //Toast.makeText(context,"scheduling alart for: "+mealType+" at :"+hour,Toast.LENGTH_SHORT).show()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, MealAlarmBroadcastReceiver::class.java)

        val mealName = mealType.getName(context)
        intent.putExtra("MESSAGE","Está quase na hora do seu ${mealName}")
        intent.putExtra("MEAL_TYPE_ORDINAL",mealType.ordinal)

        val alarmIntent = PendingIntent.getBroadcast(context, idForMealType(mealType), intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                hour.time,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        )
    }
    fun cancelReminder(context: Context,mealType: MealType){
        val intent = Intent(context, MealAlarmBroadcastReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, idForMealType(mealType), intent, 0)
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(alarmIntent)
    }

    private fun idForMealType(mealType: MealType) : Int{
        return mealType.ordinal + 100
    }
}