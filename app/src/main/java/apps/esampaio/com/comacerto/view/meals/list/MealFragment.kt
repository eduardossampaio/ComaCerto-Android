package apps.esampaio.com.comacerto.view.meals.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.meals.list.adapter.ListDailyMealRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_daily_meals.*

class MealFragment : BaseFragment() {
    var mealList: Array<Meal> = emptyArray()
    var fragmentPosition : Int? = 0;
    lateinit var dailyMealsRecyclerView: RecyclerView
    lateinit var noMealsRegisteredTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailyMealsRecyclerView = daily_meals_recycler_view
        noMealsRegisteredTextView = no_meals_registered_text_view
        dailyMealsRecyclerView.layoutManager = LinearLayoutManager(activity)
        dailyMealsRecyclerView.adapter = ListDailyMealRecyclerViewAdapter(activity!!, mealList.asList())
        updateViews()
    }

    private fun updateViews() {
        mealList = arguments?.get(PARAM_MEAL_LIST) as Array<Meal>
        fragmentPosition = arguments?.getInt(PARAM_FRAGMENT_LIST_POSITION)
        update(mealList)

    }

    fun getFragmentPosition() : Int{
       return if (fragmentPosition != null)  fragmentPosition!! else -1
    }

    fun update(mealList: Array<Meal>) {
        this.mealList = mealList
        if (mealList.isEmpty()) {
            dailyMealsRecyclerView.visibility = View.GONE
            noMealsRegisteredTextView.visibility = View.VISIBLE
        } else {
            dailyMealsRecyclerView.visibility = View.VISIBLE
            noMealsRegisteredTextView.visibility = View.GONE
            (dailyMealsRecyclerView.adapter as ListDailyMealRecyclerViewAdapter).mealList = mealList.asList()
            (dailyMealsRecyclerView.adapter as ListDailyMealRecyclerViewAdapter).notifyDataSetChanged()
        }
    }

    companion object {
        private const val PARAM_MEAL_LIST = "PARAM_MEAL_LIST"
        private const val PARAM_FRAGMENT_LIST_POSITION = "PARAM_FRAGMENT_LIST_POSITION"
        @JvmStatic
        fun newInstance(mealList: List<Meal>,position: Int) = MealFragment().apply {
            val args = Bundle()
            val mealsArray = mealList.toTypedArray()
            args.putSerializable(PARAM_MEAL_LIST, mealsArray)
            args.putSerializable(PARAM_FRAGMENT_LIST_POSITION, position)
            setArguments(args)
        }
    }
}