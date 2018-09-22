package apps.esampaio.com.comacerto.view.settings

import android.os.Bundle
import android.support.v14.preference.SwitchPreference
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.extensions.appendTime
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.fromFormat
import apps.esampaio.com.comacerto.core.service.preferences.PreferencesService
import apps.esampaio.com.comacerto.core.service.preferences.update
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import apps.esampaio.com.comacerto.view.extensions.setStartTime
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var enableReminders: SwitchPreference
    lateinit var breakfastReminder: Preference
    lateinit var lunchReminder: Preference
    lateinit var snackReminder: Preference
    lateinit var dinnerReminder: Preference

    lateinit var preferenceService: PreferencesService

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        preferenceService = PreferencesService(context!!)

        enableReminders = findPreference(PreferencesService.PREFERENCE_ENABLE_REMINDERS_KEY) as SwitchPreference

        breakfastReminder = findPreference(PreferencesService.PREFERENCE_BREAKFAST_REMINDER_TIME_KEY)
        lunchReminder = findPreference(PreferencesService.PREFERENCE_LUNCH_REMINDER_TIME_KEY)
        snackReminder = findPreference(PreferencesService.PREFERENCE_SNACK_REMINDER_TIME_KEY)
        dinnerReminder = findPreference(PreferencesService.PREFERENCE_DINNER_REMINDER_TIME_KEY)

        enableReminders.setOnPreferenceChangeListener { preference, any ->
            enableMealRemindersIfChecked(any as Boolean)
            (context as BaseActivity).showError("Essa funcionalidade está em desenvolvimento e estará dispoível em breve")
            true
        }
        breakfastReminder.setOnPreferenceClickListener {
            openTimeDialog(breakfastReminder)
            true
        }
        lunchReminder.setOnPreferenceClickListener {
            openTimeDialog(lunchReminder)
            true
        }
        snackReminder.setOnPreferenceClickListener {
            openTimeDialog(snackReminder)
            true
        }
        dinnerReminder.setOnPreferenceClickListener {
            openTimeDialog(dinnerReminder)
            true
        }

        enableMealRemindersIfChecked(enableReminders.isChecked)
        setSummaryForTimesPref(breakfastReminder)
        setSummaryForTimesPref(lunchReminder)
        setSummaryForTimesPref(snackReminder)
        setSummaryForTimesPref(dinnerReminder)
    }

    private fun enableMealRemindersIfChecked(enable: Boolean) {
        breakfastReminder.isEnabled = enable
        lunchReminder.isEnabled = enable
        snackReminder.isEnabled = enable
        dinnerReminder.isEnabled = enable
    }

    private fun setSummaryForTimesPref(preference: Preference){
        preference.summary = preferenceService.getPreferenceString(preference.key,preference.summary.toString())
    }

    private fun openTimeDialog(targetPreference: Preference) {

        val supportFragmentManager = (context as AppCompatActivity).supportFragmentManager
        val rtpd = RadialTimePickerDialogFragment()
                .setOnTimeSetListener(object : RadialTimePickerDialogFragment.OnTimeSetListener {
                    override fun onTimeSet(dialog: RadialTimePickerDialogFragment?, hourOfDay: Int, minute: Int) {
                        val newDate = Calendar.getInstance().appendTime(Date(),hourOfDay,minute)
                        val dateText = newDate.asString("HH:mm")
                        targetPreference.summary = dateText
                        targetPreference.update(dateText)
                    }
                })
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
