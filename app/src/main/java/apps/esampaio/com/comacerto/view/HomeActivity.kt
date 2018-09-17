
package apps.esampaio.com.comacerto.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.help.HelpFragment
import apps.esampaio.com.comacerto.view.meals.list.ListMealsFragment
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import apps.esampaio.com.comacerto.view.report.GenerateReportFragment
import apps.esampaio.com.comacerto.view.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    var lastFragment : Fragment? = null
    val listMealFragment = ListMealsFragment.newInstance()
    val generateReportFragment = GenerateReportFragment.newInstance()
    val settingsFragment = SettingsFragment.newInstance()
    val helpFragment = HelpFragment.newInstance()
    private var newMealOption: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.navigation_meal_list -> {
                    openFragment(listMealFragment)
                }
                R.id.navigation_meal_reports -> {
                    openFragment(generateReportFragment)
                }
                R.id.navigation_meal_settings -> {
                    openFragment(settingsFragment)
                }
                R.id.navigation_meal_help -> {
                    openFragment(helpFragment)
                }
            }
            true
        }
        openFragment(listMealFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.meals_main_menu,menu)
        newMealOption = menu?.findItem(R.id.add_new_meal_menu_item)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.add_new_meal_menu_item){
            val intent = Intent(this,AddNewMealActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun openFragment(fragment: Fragment) {
        if (lastFragment == fragment){
            return
        }

        val transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(R.anim.slide_in_left, 0);

        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        Log.d("HomeActivity","Fragment size:"+ supportFragmentManager.fragments.size)


        if (fragment == listMealFragment) {
            Handler().postDelayed({
                newMealOption?.setVisible(true)
            }, 200)
        } else {
            newMealOption?.setVisible(false)
        }


    }

    override fun onBackPressed() {

    }
}
