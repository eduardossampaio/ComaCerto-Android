package apps.esampaio.com.comacerto.view.meals.list.adater2

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.view.custom.SmartFragmentStatePagerAdapter
import java.util.*

class ListMealFragmentPageViewAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    var allMeals = mutableMapOf<Int,List<Meal>>()
    var allWaters = mutableMapOf<Int,List<Water>>()
    var itemCount = 0

    override fun getItem(position: Int): Fragment {

//        val currentFragment = this.getRegisteredFragment(position) as DailyMealFragment?
//
//        if (currentFragment != null){
//            currentFragment.updateData(allMeals[position],allWaters[position])
////            currentFragment.updateViews()
//
//            return currentFragment
//        }
        return DailyMealFragment.newInstance(Date())
    }

    init{
        itemCount = Date(System.currentTimeMillis()).dayOfYear() + 1
    }

    override fun getItemPosition(`object`: Any): Int {
        return  POSITION_NONE;
    }

    override fun getCount(): Int {
        return itemCount
    }


    fun updateData(position: Int, mealList: List<Meal>, waterList: List<Water>) {
//
//        try {
//            allMeals[position] = mealList
//            allWaters[position] = waterList
//
//            val currentFragment = this.getRegisteredFragment(position) as DailyMealFragment
//            currentFragment.updateData(mealList, waterList)
//        } catch (e: Exception) {
//
//        }
    }
}
