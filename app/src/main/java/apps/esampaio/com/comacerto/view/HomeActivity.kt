
package apps.esampaio.com.comacerto.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.help.HelpFragment
import apps.esampaio.com.comacerto.view.meals.list.ListMealsFragment
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
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
                }
                R.id.navigation_meal_reports -> {
                    displayGenerateReportsFragment()
                }
                R.id.navigation_meal_settings -> {
                    displaySettingsFragment()
                }
                R.id.navigation_meal_help -> {
                    displayHelpFragment()

                }
            }
            true
        }
        displayMealFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val isNewMeal = true
        if (isNewMeal) {
            menuInflater.inflate(R.menu.meals_main_menu,menu)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.add_new_meal_menu_item){
            val intent = Intent(this,AddNewMealActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
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
