package apps.esampaio.com.comacerto.view.meals.list.adater2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.core.service.meal.MealService
import apps.esampaio.com.comacerto.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_daily_meals.*
import java.util.*

class DailyMealFragment : BaseFragment(),MealPresenter {

    companion object {

        val DATE_PARAM = "DATE_PARAM"
        fun newInstance(date:Date) = DailyMealFragment().apply {
            val args = Bundle()
            args.putSerializable(DATE_PARAM,date)
            setArguments(args)
        }
    }

    var mealsOfDay = emptyList<Meal>()
    var waterOfDay = emptyList<Water>()

    var date:Date? = null
    lateinit var mealService:MealService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("DailyMealFragment","onCreateView")
        if ( arguments !=  null) {
            this.date = arguments!!.get(DATE_PARAM) as Date
//            val mealsPassed = arguments!!.get(MEALS_OF_DAY_PARAM) as Array<Meal>?
//            val waterPassed = arguments!!.get(WATERS_OF_DAY_PARAM) as Array<Water>?
//
//            if (mealsPassed != null){
//                this.mealsOfDay = mealsPassed.toMutableList()
//            }
//
//            if (waterPassed != null){
//                this.waterOfDay = waterPassed.toMutableList()
//            }
        }

        mealService = MealService(this)
        return LayoutInflater.from(context).inflate(R.layout.fragment_daily_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("DailyMealFragment","onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        updateViews(mealsOfDay,waterOfDay)
    }

    override fun onResume() {
        Log.d("DailyMealFragment","onResume")
        super.onResume()
        if(date!=null) {
            mealService.dateSelected(date!!)
        }
//        updateViews()
    }

    override fun updateMealAndWaterList(meals: List<Meal>, water: List<Water>) {
        if (mealsOfDay!=null) {
            this.mealsOfDay = meals
        }
        if (waterOfDay!=null) {
            this.waterOfDay = water
        }
        updateViews()
    }

    fun  updateViews(){
        Handler(Looper.getMainLooper()).run {
            updateViews(mealsOfDay,waterOfDay)
        }
    }
    fun  updateViews( mealsOfDay:List<Meal>,waterOfDay:List<Water>){


        val mealListRecyclerView = daily_meals_recycler_view
        val noMealTextView = no_meals_registered_text_view

        val empty = (mealsOfDay == null || mealsOfDay.isEmpty()) && (waterOfDay == null || waterOfDay.isEmpty())
        if (empty) {
            mealListRecyclerView?.visibility = View.GONE
            noMealTextView?.visibility = View.VISIBLE
        } else {
            mealListRecyclerView?.visibility = View.VISIBLE
            noMealTextView?.visibility = View.GONE

            var listAdapter = mealListRecyclerView?.adapter as ListDailyMealRecyclerViewAdapter?
            if ( listAdapter == null) {
            mealListRecyclerView?.adapter = ListDailyMealRecyclerViewAdapter(context!!, mealsOfDay!!, waterOfDay!!)
            mealListRecyclerView?.layoutManager = LinearLayoutManager(context!!)
            }else{
                listAdapter.updateItems(mealsOfDay!!,waterOfDay!!)
                listAdapter.notifyDataSetChanged()

            }

        }
    }


}