package apps.esampaio.com.comacerto.view.meals.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.meals.list.adapter.FeelingsListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.list.adapter.MealListRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_card_view_how_i_feel.*
import kotlinx.android.synthetic.main.layout_card_view_meal_type.*

class AddNewMealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_meal)
        setupMealTypeList()
        setupFeelingsList()
    }

    private fun setupMealTypeList(){
        meal_list_rv.adapter = MealListRecyclerViewAdapter(this)
        meal_list_rv.layoutManager = GridLayoutManager(this,4)
    }
    private fun setupFeelingsList(){
        feeling_list_rv.adapter = FeelingsListRecyclerViewAdapter(this)
        feeling_list_rv.layoutManager = GridLayoutManager(this,4)
    }
}
