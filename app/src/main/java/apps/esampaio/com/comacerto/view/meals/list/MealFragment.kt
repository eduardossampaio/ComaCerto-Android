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
    var mealList:Array<Meal> = emptyArray()
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

    private fun updateViews(){
        mealList = arguments?.get(PARAM_MEAL_LIST) as Array<Meal>
        Log.d("MealFragment","updateViews")
        if (mealList.isEmpty()){
            dailyMealsRecyclerView?.visibility = View.GONE
            noMealsRegisteredTextView?.visibility = View.VISIBLE
            noMealsRegisteredTextView?.append(".")
        }else {
            dailyMealsRecyclerView?.visibility = View.VISIBLE
            noMealsRegisteredTextView?.visibility = View.GONE
            dailyMealsRecyclerView?.layoutManager = LinearLayoutManager(activity)
            dailyMealsRecyclerView?.adapter = ListDailyMealAdapter(activity!!, mealList.asList())
        }

    }

    companion object {
        private const val PARAM_MEAL_LIST = "PARAM_MEAL_LIST"
        @JvmStatic
        fun newInstance(mealList:List<Meal>) = MealFragment().apply {
            val args = Bundle()
            val mealsArray = mealList.toTypedArray()
            args.putSerializable(PARAM_MEAL_LIST, mealsArray )
            setArguments(args)
        }
    }
}