package apps.esampaio.com.comacerto.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.firebase.RemoteConfig
import apps.esampaio.com.comacerto.core.service.preferences.PreferencesService
import apps.esampaio.com.comacerto.view.dialogs.Dialogs
import kotlinx.android.synthetic.main.activity_user_terms.*

class UserTermsActivity : AppCompatActivity() {
    private var currentUserTermsVersion: Int = 0
    private lateinit var lastAcceptedUserTermsUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_terms)
        currentUserTermsVersion = RemoteConfig.getInstance().lastUserTermsVersion
        lastAcceptedUserTermsUrl = RemoteConfig.getInstance().getUserTermsURL(currentUserTermsVersion)

        if(lastAcceptedUserTermsUrl.isBlank()){
            Dialogs.openErrorDialog(this,getString(R.string.no_internet_connection)) {

//                finishAffinity();
//                System.exit(0);
                finish();
            }
        }else {
            terms_and_conditions_view.loadFromUrl(lastAcceptedUserTermsUrl)

            checkBox_agree_user_terms.setOnCheckedChangeListener { compoundButton, checked ->
                button_confirm_agree_terms.isEnabled = checked;
            }

            button_confirm_agree_terms.setOnClickListener {
                PreferencesService(this).updateLastAcceptedUserTermsVersion(currentUserTermsVersion);
                finish();
            }
        }
    }

    override fun onBackPressed() {
    }
}
