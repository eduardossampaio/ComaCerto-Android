package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.view.meals.list.MealFragment
import java.util.*
import android.provider.SyncStateContract.Helpers.update



class  DailyMealViewPager(fragmentManager:FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    var itemCount = 0
    var mealList = listOf<Meal>()
    init {
       itemCount = Date(System.currentTimeMillis()).dayOfYear() + 1
    }
    fun updateFragment(selected: Int, mealList:List<Meal>) {
       val fragment =  getItem(selected) as MealFragment
        fragment.update(mealList)
    }


    override fun getItem(index: Int): Fragment {
        return MealFragment.newInstance()
    }

    override fun getCount(): Int {
       return itemCount
    }


}