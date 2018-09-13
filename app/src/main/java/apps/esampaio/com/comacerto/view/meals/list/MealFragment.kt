package apps.esampaio.com.comacerto.view.meals.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.meals.list.adapter.ListDailyMealAdapter
import kotlinx.android.synthetic.main.fragment_daily_meals.*

class MealFragment : BaseFragment() {
    var mealList:List<Meal> = emptyList()
    var dailyMealsRecyclerView : RecyclerView? = null
    var noMealsRegisteredTextView : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailyMealsRecyclerView = daily_meals_recycler_view
        noMealsRegisteredTextView = no_meals_registered_text_view
        updateViews()
    }
    fun update(mealList: List<Meal>){
        this.mealList = mealList
        updateViews()
    }
    private fun updateViews(){
        Log.d("MealFragment","updateViews")
        if (mealList.isEmpty()){
            dailyMealsRecyclerView?.visibility = View.GONE
            noMealsRegisteredTextView?.visibility = View.VISIBLE
            noMealsRegisteredTextView?.append(".")
        }else {
            dailyMealsRecyclerView?.visibility = View.VISIBLE
            noMealsRegisteredTextView?.visibility = View.GONE
            dailyMealsRecyclerView?.layoutManager = LinearLayoutManager(activity)
            dailyMealsRecyclerView?.adapter = ListDailyMealAdapter(activity!!, mealList)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = MealFragment().apply {

        }
    }
}