package apps.esampaio.com.comacerto.view.meals.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.core.service.meal.MealService
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.custom.DateListView
import apps.esampaio.com.comacerto.view.meals.list.adapter.DailyMealViewPager
import kotlinx.android.synthetic.main.date_list_view.view.*
import kotlinx.android.synthetic.main.fragment_list_meals.*
import java.util.*
import kotlin.math.log


class ListMealsFragment : BaseFragment(), ViewPager.OnPageChangeListener, DateListView.DayItemSelectedListener,MealPresenter {

    var currentItemPosition = 0
    var mealIterator = MealService(this)
    var adapter : DailyMealViewPager? = null

    override fun updateMealList(meals: List<Meal>) {
        adapter?.updateFragment(currentItemPosition,meals)
    }

    override fun daySelected(day: Date) {
        val position = day.dayOfYear()
        Log.d("ListMealsFragment","day selected from header. day:${day} position: ${position}")
        currentItemPosition = position
        daily_meal_view_pager.currentItem = position
        newDaySelected(day)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(pageIndex: Int, position1: Float, position2: Int) {

    }

    fun newDaySelected(day: Date){
        mealIterator.dateSelected(day)
    }
    override fun onPageSelected(pageIndex: Int) {
        val newPosition = pageIndex;
        if ( newPosition < currentItemPosition){
            navigation_header.selectNextDay()
        }else if (newPosition > currentItemPosition){
            navigation_header.selectPreviousDay()
        }
        currentItemPosition = newPosition
        newDaySelected(navigation_header.selectedDay)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.adapter =  DailyMealViewPager(childFragmentManager)
        daily_meal_view_pager.adapter = adapter
        daily_meal_view_pager.currentItem = adapter!!.count -1
        daily_meal_view_pager.addOnPageChangeListener(this)
        currentItemPosition = daily_meal_view_pager.currentItem
        navigation_header.onDayItemSelectedListener = this
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMealsFragment().apply {

        }
    }
}
