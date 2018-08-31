package apps.esampaio.com.comacerto.view.meals.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.meals.register.adapter.FeelingsListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.ListFoodRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.MealListRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_card_view_how_i_feel.*
import kotlinx.android.synthetic.main.layout_card_view_meal_type.*
import kotlinx.android.synthetic.main.layout_card_view_what_i_ate.*

class AddNewMealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_meal)
        setupMealTypeList()
        setupFeelingsList()
        setupFoodsList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_meal_menu,menu)
        return true
    }
    private fun setupMealTypeList(){
        meal_list_rv.adapter = MealListRecyclerViewAdapter(this)
        meal_list_rv.layoutManager = GridLayoutManager(this,4)
    }
    private fun setupFeelingsList(){
        feeling_list_rv.adapter = FeelingsListRecyclerViewAdapter(this)
        feeling_list_rv.layoutManager = GridLayoutManager(this,4)
        (feeling_list_rv.adapter as FeelingsListRecyclerViewAdapter).notifyDataSetChanged()
    }
    private fun setupFoodsList(){
        val adapter = ListFoodRecyclerViewAdapter(this)
        foods_list_rv.adapter = adapter
        foods_list_rv.layoutManager = LinearLayoutManager(this)
        adapter.addNewRow()
        adapter.addNewRow()
        adapter.addNewRow()
        adapter.addNewRow()
    }
}
