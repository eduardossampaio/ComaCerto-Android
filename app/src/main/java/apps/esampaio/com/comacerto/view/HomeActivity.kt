
package apps.esampaio.com.comacerto.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.help.HelpFragment
import apps.esampaio.com.comacerto.view.meals.list.ListMealFragment2
import apps.esampaio.com.comacerto.view.meals.list.ListMealsFragment
import apps.esampaio.com.comacerto.view.report.GenerateReportFragment
import apps.esampaio.com.comacerto.view.settings.SettingsFragment
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {
    var lastFragment : Fragment? = null

    val listMealFragment = ListMealFragment2.newInstance()

    val generateReportFragment = GenerateReportFragment.newInstance()
    val settingsFragment = SettingsFragment.newInstance()
    val helpFragment = HelpFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
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
//        MobileAds.initialize(this, getString(R.string.admob_app_key));
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
        bottom_navigation_view.getMenu().findItem(bottom_navigation_view.getSelectedItemId())
    }

    override fun onBackPressed() {
        finish()
    }
}
