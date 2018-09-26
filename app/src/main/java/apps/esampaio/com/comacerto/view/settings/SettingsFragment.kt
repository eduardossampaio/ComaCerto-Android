package apps.esampaio.com.comacerto.view.settings

import android.os.Bundle
import android.support.v14.preference.SwitchPreference
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.extensions.appendTime
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.fromFormat
import apps.esampaio.com.comacerto.core.service.preferences.PreferencesService
import apps.esampaio.com.comacerto.core.service.preferences.update
import apps.esampaio.com.comacerto.core.service.reminder.ReminderService
import apps.esampaio.com.comacerto.view.extensions.setStartTime
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {

    lateinit var breakfastReminder: SwitchPreference
    lateinit var lunchReminder: SwitchPreference
    lateinit var snackReminder: SwitchPreference
    lateinit var dinnerReminder: SwitchPreference

    lateinit var preferenceService: PreferencesService

    val reminderService = ReminderService()


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        breakfastReminder = findPreference(PreferencesService.PREFERENCE_BREAKFAST_REMINDER_TIME_KEY) as SwitchPreference
        lunchReminder = findPreference(PreferencesService.PREFERENCE_LUNCH_REMINDER_TIME_KEY) as SwitchPreference
        snackReminder = findPreference(PreferencesService.PREFERENCE_SNACK_REMINDER_TIME_KEY) as SwitchPreference
        dinnerReminder = findPreference(PreferencesService.PREFERENCE_DINNER_REMINDER_TIME_KEY) as SwitchPreference

        breakfastReminder.onPreferenceChangeListener = OnMealPreferenceChanceListener(MealType.Breakfast)
        lunchReminder.onPreferenceChangeListener = OnMealPreferenceChanceListener(MealType.Lunch)
        snackReminder.onPreferenceChangeListener = OnMealPreferenceChanceListener(MealType.Snack)
        dinnerReminder.onPreferenceChangeListener = OnMealPreferenceChanceListener(MealType.Dinner)



    }

    inner class OnMealPreferenceChanceListener(val mealType: MealType) : Preference.OnPreferenceChangeListener {
        override fun onPreferenceChange(preference: Preference?, value: Any?): Boolean {

            if ( preference is SwitchPreference && value is Boolean){
                if (value){
                    openTimeDialog(preference,mealType)
                }else{

                }
            }
            return true;
        }


    }




    private fun preferenceValueAsDate(preference: Preference) : Date{
        return Date().fromFormat("HH:mm",preference.summary.toString())
    }
    private fun openTimeDialog(targetPreference: SwitchPreference,mealType: MealType) {

        val supportFragmentManager = (context as AppCompatActivity).supportFragmentManager
        val rtpd = RadialTimePickerDialogFragment()
                .setOnTimeSetListener(object : RadialTimePickerDialogFragment.OnTimeSetListener {
                    override fun onTimeSet(dialog: RadialTimePickerDialogFragment?, hourOfDay: Int, minute: Int) {
                        val newDate = Calendar.getInstance().appendTime(Date(),hourOfDay,minute)
                        val dateText = newDate.asString("HH:mm")
                        targetPreference.summary = dateText
//                        targetPreference.update(dateText)
//                        reminderService.scheduleReminder(context!!,mealType,newDate)
                    }
                }).setOnDismissListener {

//                    targetPreference.isChecked = false
                }

        try{
            val dateFormat = preferenceService.getPreferenceString(targetPreference.key, targetPreference.summary.toString())
            rtpd.setStartTime(Date().fromFormat("HH:mm",dateFormat))
        }catch (e:Exception){

        }

        rtpd.show(supportFragmentManager, AddNewMealActivity.FRAG_TAG_TIME_PICKER)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment().apply {

        }
    }
}
