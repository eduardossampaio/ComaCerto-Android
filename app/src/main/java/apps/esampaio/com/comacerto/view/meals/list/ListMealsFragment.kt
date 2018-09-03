package apps.esampaio.com.comacerto.view.meals.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.view.custom.DateListView
import apps.esampaio.com.comacerto.view.meals.list.adapter.DailyMealViewPager
import kotlinx.android.synthetic.main.fragment_list_meals.*
import java.util.*


class ListMealsFragment : Fragment(), ViewPager.OnPageChangeListener, DateListView.DayItemSelectedListener {
    override fun daySelected(day: Date, position: Int) {
        currentItemPosition = position
        daily_meal_view_pager.currentItem = position
    }

    var currentItemPosition = 0

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(pageIndex: Int, position1: Float, position2: Int) {

    }

    override fun onPageSelected(pageIndex: Int) {
        val newPosition = pageIndex;
        if ( newPosition < currentItemPosition){
            navigation_header.selectNextDay()
        }else if (newPosition > currentItemPosition){
            navigation_header.selectPreviousDay()
        }
        currentItemPosition = newPosition

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =  DailyMealViewPager(childFragmentManager)
        daily_meal_view_pager.adapter = adapter
        daily_meal_view_pager.currentItem = adapter.count -1
        daily_meal_view_pager.addOnPageChangeListener(this)
        currentItemPosition = daily_meal_view_pager.currentItem
        navigation_header.updateSelectedDayItem(currentItemPosition)
        navigation_header.onDayItemSelectedListener = this
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMealsFragment().apply {

        }
    }
}
