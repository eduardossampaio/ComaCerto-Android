package apps.esampaio.com.comacerto.core.service.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.extensions.sameHour
import apps.esampaio.com.comacerto.core.service.NotificationService
import apps.esampaio.com.comacerto.core.service.preferences.PreferencesService
import java.util.*


class MealAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
//            Toast.makeText(cont/xt,"Displaying notification for reminder", Toast.LENGTH_SHORT).show()
            var message = intent!!.extras.getString("MESSAGE")
            var mealOrdinal = intent!!.extras.getInt("MEAL_TYPE_ORDINAL")
            val mealType = MealType.getByOrdinal(mealOrdinal)
            val mealTypeReminderTime = PreferencesService(context).getMealTimeReminder(mealType)
            val currentTime = Date()
            if(mealTypeReminderTime!= null && currentTime.sameHour(mealTypeReminderTime)){
                NotificationService.displayMealReminderNotification(context, mealType, message)
            }
        }
    }
}