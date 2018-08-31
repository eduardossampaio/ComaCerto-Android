package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import apps.esampaio.com.comacerto.view.meals.list.MealFragment

class  DailyMealViewPager(fragmentManager:FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(index: Int): Fragment {
        return MealFragment.newInstance()
    }

    override fun getCount(): Int {
       return 5
    }

}