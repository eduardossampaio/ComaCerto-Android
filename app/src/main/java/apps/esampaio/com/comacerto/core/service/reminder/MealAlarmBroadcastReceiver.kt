package apps.esampaio.com.comacerto.core.service.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.service.NotificationService
import org.jetbrains.anko.runOnUiThread


class MealAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(context!= null) {
            context.runOnUiThread {
                var message = intent!!.extras.getString("MESSAGE")
                var mealOrdinal = intent!!.extras.getInt("MEAL_TYPE_ORDINAL")
                Toast.makeText(context, "notificando: ${message}", Toast.LENGTH_SHORT).show()
                NotificationService.displayMealReminderNotification(context, MealType.getByOrdinal(mealOrdinal), message)
            }
        }
    }
}