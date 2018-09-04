package apps.esampaio.com.comacerto.view.meals.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import apps.esampaio.com.comacerto.R

class AddNewMealActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_meal_2)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_meal_menu,menu)
        return true
    }

//    private fun setupFeelingsList(){
//        feeling_list_rv.adapter = FeelingsListRecyclerViewAdapter(this)
//        feeling_list_rv.layoutManager = GridLayoutManager(this,4)
//        (feeling_list_rv.adapter as FeelingsListRecyclerViewAdapter).notifyDataSetChanged()
//    }
//    private fun setupFoodsList(){
//        val adapter = ListFoodRecyclerViewAdapter(this)
//        foods_list_rv.adapter = adapter
//        foods_list_rv.layoutManager = LinearLayoutManager(this)
//        adapter.addNewRow()
//        adapter.addNewRow()
//        adapter.addNewRow()
//        adapter.addNewRow()
//    }
}
