package apps.esampaio.com.comacerto.view.settings.subsettings

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.adapter.FeelingsListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.ImageRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.MealListRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_manage_meals.*

class ManageMealsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_meals)

        default_meal_list_rv.layoutManager = GridLayoutManager(this,5);
        default_meal_list_rv.adapter = MealListRecyclerViewAdapter(this)
    }
}