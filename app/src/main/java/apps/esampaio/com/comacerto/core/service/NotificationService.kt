package apps.esampaio.com.comacerto.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity

class NotificationService {

    companion object {
        private val MEAL_REMINDER_CHANEL_ID = "MEAL_REMINDER_CHANEL"

        private fun createNotificationChannel(context: Context,chanelId:String,chanelName:String,chanelDescription: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(chanelId, chanelName, importance)
                channel.description = chanelDescription
                val notificationManager = context?.getSystemService(NotificationManager::class.java)
                notificationManager!!.createNotificationChannel(channel)
            }
        }

        fun displayMealReminderNotification(context: Context,mealType: MealType,message:String){
            createNotificationChannel(
                    context,
                    MEAL_REMINDER_CHANEL_ID,
                    context.getString(R.string.meal_reminder_chanel_name),
                    context.getString(R.string.meal_reminder_chanel_description))
            val meal = Meal()
            meal.mealType = mealType
            val startNewMealIntent = AddNewMealActivity.buildIntent(context, meal)
            val startNewMealPendingIntent = PendingIntent.getActivity(context,0,startNewMealIntent,0);
            val mBuilder = NotificationCompat.Builder(context, MEAL_REMINDER_CHANEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_icon)
                    .setChannelId(MEAL_REMINDER_CHANEL_ID)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(startNewMealPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(mealType.ordinal + 100, mBuilder.build())

        }
    }
}