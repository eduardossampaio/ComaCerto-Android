package apps.esampaio.com.comacerto.view.settings.subsettings

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.databinding.ActivityManageMealsBinding
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.adapter.FeelingsListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.ImageRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.MealListRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_manage_meals.*

class ManageMealsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_manage_meals)


       val binding = ActivityManageMealsBinding.inflate(layoutInflater);
        default_meal_list_rv.layoutManager = GridLayoutManager(this,5);
        default_meal_list_rv.adapter = object : MealListRecyclerViewAdapter(this){
            override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapterViewHolder, i: Int) {
                super.selectedItem = -1;
                super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i);
            }
        }

        setContentView(binding.root)
    }
}