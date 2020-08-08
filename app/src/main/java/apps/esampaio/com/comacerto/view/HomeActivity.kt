
package apps.esampaio.com.comacerto.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.firebase.RemoteConfig
import apps.esampaio.com.comacerto.core.service.preferences.PreferencesService
import apps.esampaio.com.comacerto.view.dialogs.Dialogs
import apps.esampaio.com.comacerto.view.dialogs.NewsDialog
import apps.esampaio.com.comacerto.view.help.HelpFragment
import apps.esampaio.com.comacerto.view.meals.list.ListMealsFragment
import apps.esampaio.com.comacerto.view.report.GenerateReportFragment
import apps.esampaio.com.comacerto.view.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {
    var lastFragment : Fragment? = null

    val listMealFragment = ListMealsFragment.newInstance()

    val generateReportFragment = GenerateReportFragment.newInstance()
    val settingsFragment = SettingsFragment.newInstance()
    val helpFragment = HelpFragment.newInstance()

    lateinit var preferencesService : PreferencesService;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyTermsAndConditions();
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
        preferencesService = PreferencesService(this);
        openFragment(listMealFragment)
        showNewsPopup()
//        MobileAds.initialize(this, getString(R.string.admob_app_key));
    }

    private fun showNewsPopup() {
        if(!preferencesService.haveShowedNewsPopup()){
            NewsDialog(this).show()
            preferencesService.setHaveShowedNewsPopup()
        }
    }

    private fun verifyTermsAndConditions() {
        val lastAcceptedUserTermsVersion = PreferencesService(this).getLastAcceptedUserTermsVersion();
        //never accepted terms
        val lastUserTermsVersion = RemoteConfig.getInstance().lastUserTermsVersion;
        if (lastAcceptedUserTermsVersion == null || lastAcceptedUserTermsVersion == 0 || lastAcceptedUserTermsVersion < lastAcceptedUserTermsVersion){
            startActivity(Intent(this,UserTermsActivity::class.java))
        }
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
