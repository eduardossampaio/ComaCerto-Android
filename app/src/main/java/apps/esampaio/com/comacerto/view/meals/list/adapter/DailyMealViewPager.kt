package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.view.meals.list.MealFragment
import java.util.*

class  DailyMealViewPager(fragmentManager:FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    var itemCount = 0

    init {
       itemCount = Date(System.currentTimeMillis()).dayOfYear() + 1
    }

    override fun getItem(index: Int): Fragment {
        return MealFragment.newInstance()
    }

    override fun getCount(): Int {
       return itemCount
    }


}