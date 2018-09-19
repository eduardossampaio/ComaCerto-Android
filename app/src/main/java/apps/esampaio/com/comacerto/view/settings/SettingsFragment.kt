package apps.esampaio.com.comacerto.view.settings

import android.os.Bundle
import android.support.v14.preference.SwitchPreference
import android.support.v7.preference.Preference

import android.support.v7.preference.PreferenceFragmentCompat
import android.widget.Toast
import apps.esampaio.com.comacerto.R


class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var enableReminders : SwitchPreference
    lateinit var breakfastReminder : Preference
    lateinit var lunchReminder : Preference
    lateinit var snackReminder : Preference
    lateinit var dinnerReminder : Preference


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        enableReminders = findPreference("PREFERENCE_ENABLE_REMINDER") as SwitchPreference

        breakfastReminder = findPreference("PREFERENCE_BREAKFAST_REMINDER_TIME")
        lunchReminder = findPreference("PREFERENCE_LUNCH_REMINDER_TIME")
        snackReminder = findPreference("PREFERENCE_SNACK_REMINDER_TIME")
        dinnerReminder = findPreference("PREFERENCE_DINNER_REMINDER_TIME")

        enableReminders.setOnPreferenceChangeListener { preference, any ->
            enableMealRemindersIfChecked(any as Boolean)
            true
        }
        enableMealRemindersIfChecked(enableReminders.isChecked)
    }

    private fun enableMealRemindersIfChecked(enable:Boolean){
        breakfastReminder.isEnabled = enable
        lunchReminder.isEnabled = enable
        snackReminder.isEnabled = enable
        dinnerReminder.isEnabled = enable
    }


    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment().apply {

        }
    }
}
