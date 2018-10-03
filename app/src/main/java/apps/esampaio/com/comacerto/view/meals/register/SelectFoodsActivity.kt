package apps.esampaio.com.comacerto.view.meals.register

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Food
import apps.esampaio.com.comacerto.core.service.food.FoodInteractor
import apps.esampaio.com.comacerto.core.service.food.FoodPresenter
import apps.esampaio.com.comacerto.core.service.food.FoodService
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.adapter.ListFoodRecyclerViewAdapter2
import kotlinx.android.synthetic.main.activity_select_foods.*


class SelectFoodsActivity : BaseActivity(), FoodPresenter {

    override fun updateDefaultFoodsList(foodsList: List<Food>) {
        setupFoodsList(foodsList.toMutableList())
    }

    lateinit var foodsListAdapter: ListFoodRecyclerViewAdapter2
    lateinit var foodIteractor:FoodInteractor

    companion object {
        val FOODS_LIST_PARAM = "FOODS_LIST_PARAM"
        val FOODS_LIST_RESULT = "FOODS_LIST_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodIteractor =  FoodService(this,this)
        setContentView(R.layout.activity_select_foods)
        val foodsList = intent.getSerializableExtra(FOODS_LIST_PARAM)  as Array<Food>
        setupFoodsList(foodsList.toMutableList())
    }

    override fun onResume() {
        super.onResume()
        foodIteractor.screenLoaded()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_foods_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    //Log.d("search","onQueryTextSubmit: ${text}")

                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    (foods_list_rv.adapter as ListFoodRecyclerViewAdapter2).filterItems(text)
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if ( item.itemId == R.id.done){
            finishFoodsSelect()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun finishFoodsSelect() {
        val resultIntent = Intent()
        resultIntent.putExtra(FOODS_LIST_RESULT,foodsListAdapter.getSelectedFoods().toTypedArray())
        setResult(Activity.RESULT_OK,resultIntent)
        finish()
    }

    private fun setupFoodsList(foodsList:MutableList<Food>) {
        foodsListAdapter  = ListFoodRecyclerViewAdapter2(this,foodsList)
        foods_list_rv.adapter = foodsListAdapter
        foods_list_rv.layoutManager = LinearLayoutManager(this)
        foods_list_rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
