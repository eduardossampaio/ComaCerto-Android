
package apps.esampaio.com.comacerto.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.help.HelpFragment
import apps.esampaio.com.comacerto.view.meals.ListMealsFragment
import apps.esampaio.com.comacerto.view.report.GenerateReportFragment
import apps.esampaio.com.comacerto.view.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.navigation_meal_list -> {
                    displayMealFragment()
                    true
                }
                R.id.navigation_meal_reports -> {
                    displayGenerateReportsFragment()
                    true
                }
                R.id.navigation_meal_settings -> {
                    displaySettingsFragment()
                    true
                }
                R.id.navigation_meal_help -> {
                    displayHelpFragment()
                    true
                }
            }
            true
        }
        displayMealFragment()
    }

    private fun displayMealFragment() {
        val fragment = ListMealsFragment.newInstance()
        openFragment(fragment)
    }

    private fun displayGenerateReportsFragment() {
        val fragment = GenerateReportFragment.newInstance()
        openFragment(fragment)
    }

    private fun displaySettingsFragment() {
        val fragment = SettingsFragment.newInstance()
        openFragment(fragment)
    }

    private fun displayHelpFragment() {
        val fragment = HelpFragment.newInstance()
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
