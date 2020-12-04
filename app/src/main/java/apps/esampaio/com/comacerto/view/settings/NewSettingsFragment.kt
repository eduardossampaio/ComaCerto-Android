package apps.esampaio.com.comacerto.view.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.settings.subsettings.ManageMealsActivity
import kotlinx.android.synthetic.main.fragment_settings_v2.*

class NewSettingsFragment : BaseFragment() {
    companion object{
        fun newInstance(): NewSettingsFragment {
            val args = Bundle()

            val fragment = NewSettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings_v2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item_manage_meal.setOnClickListener {
           val newIntent = Intent(context, ManageMealsActivity::class.java)
            startActivity(newIntent);
        }

        item_manage_feeling.setOnClickListener {
            Toast.makeText(context,"manage feeling", Toast.LENGTH_SHORT).show();
        }

        item_manage_alarms.setOnClickListener {
            Toast.makeText(context,"manage alarms", Toast.LENGTH_SHORT).show();
        }
    }
}