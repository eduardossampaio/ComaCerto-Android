
package apps.esampaio.com.comacerto.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apps.esampaio.com.comacerto.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.navigation_meal_list -> {
                    true
                }
                R.id.navigation_meal_reports -> {
                    true
                }
                R.id.navigation_meal_settings -> {
                    true
                }
                R.id.navigation_meal_help -> {
                    true
                }
            }
            true
        }
    }
}
