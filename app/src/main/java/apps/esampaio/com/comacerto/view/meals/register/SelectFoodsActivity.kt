package apps.esampaio.com.comacerto.view.meals.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Food
import apps.esampaio.com.comacerto.core.service.food.FoodInteractor
import apps.esampaio.com.comacerto.core.service.food.FoodPresenter
import apps.esampaio.com.comacerto.core.service.food.FoodService
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.adapter.ListFoodRecyclerViewAdapter2
import kotlinx.android.synthetic.main.activity_select_foods.*


class SelectFoodsActivity : BaseActivity(), FoodPresenter {

    override fun updateDefaultFoodsList(list: List<Food>) {
        setupAutocompleteFoods(list)
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        val foodsList = intent.getSerializableExtra(FOODS_LIST_PARAM)  as Array<Food>
        setupFoodsList(foodsList.toMutableList())
    }

    override fun onResume() {
        super.onResume()
        foodIteractor.screenLoaded()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_foods_menu,menu)
        val result =  super.onCreateOptionsMenu(menu)
        Handler().postDelayed({
            runOnUiThread {
                displayShowCase()
            }
        },500)
        return result
    }

    fun displayShowCase(){
//        val config = ShowcaseConfig()
//        config.delay = 0 // half second between each showcase view
//        config.maskColor = ContextCompat.getColor(this,R.color.showcase_background)
//        val SHOWCASE_ID = "SHOWCASE_ID_FOODS_LIST_kksdsfsf"
//        val sequence = MaterialShowcaseSequence(this, SHOWCASE_ID)
//        sequence.setConfig(config)
//        add_foods_edit_text.clearFocus()
//        sequence.addSequenceItem(add_foods_edit_text,
//                "Escreva o nome dos alimentos aqui, em sequida aperte Enter para adicioná-lo", "Entendi")
//
//        sequence.addSequenceItem(add_foods_edit_text,
//                "Os Alimentos apareceram aqui", "Entendi")
//
//        sequence.addSequenceItem(findViewById(R.id.save_foods_menu_item),
//                "Após escolher todos seus alimentos, clique aqui pra confirmar", "Entendi")
//
//        sequence.start()
//        sequence.setOnItemDismissedListener({ itenView, position ->
//            if(position == 0) {
//                addFoodToList("Arroz")
//                addFoodToList("Feijão")
//                addFoodToList("Carne")
//            }else if (position == 1){
//                foodsListAdapter.removeAll()
//            }
//        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.save_foods_menu_item){
            finishFoodsSelect()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun finishFoodsSelect() {
        val resultIntent = Intent()
        resultIntent.putExtra(FOODS_LIST_RESULT,foodsListAdapter.foodsList.toTypedArray())
        setResult(Activity.RESULT_OK,resultIntent)
        finish()
    }

    private fun setupFoodsList(foodsList:MutableList<Food>) {
        foodsListAdapter  = ListFoodRecyclerViewAdapter2(this)
        foodsListAdapter.foodsList = foodsList;
        foods_list_rv.adapter = foodsListAdapter
        foods_list_rv.layoutManager = LinearLayoutManager(this)
        foods_list_rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setupAutocompleteFoods(foods:List<Food>) {
        val adapter = ArrayAdapter<Food>(this, android.R.layout.simple_dropdown_item_1line, foods)
        add_foods_edit_text.setAdapter(adapter)

        add_foods_edit_text.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addFoodToList(add_foods_edit_text.text.toString())
                add_foods_edit_text.setText("")
            }
            true
        }
    }

    private fun addFoodToList(foodName: String) {
        foodsListAdapter.addFood(foodName)
    }
}
