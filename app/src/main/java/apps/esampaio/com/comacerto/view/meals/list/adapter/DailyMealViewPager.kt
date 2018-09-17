package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.view.meals.list.MealFragment
import java.util.*


class  DailyMealViewPager(fragmentManager:FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    var itemCount = 0
    var mealsMap = hashMapOf<Int,List<Meal>>()

    init {
       itemCount = Date(System.currentTimeMillis()).dayOfYear() + 1
    }

    fun updateFragment(selected: Int, mealList:List<Meal>) {
        mealsMap[selected] = mealList
        notifyDataSetChanged()
    }

    override fun getItem(index: Int): Fragment {
        var mealList = mealsMap[index]
        if (mealList == null){
            mealList = emptyList()
        }
        return MealFragment.newInstance(mealList,index)
    }

    override fun getCount(): Int {
       return itemCount
    }

    override fun getItemPosition(fragmentObject: Any): Int {

        val f = fragmentObject as MealFragment
        val fragmentPosition = fragmentObject.getFragmentPosition()
        if (f != null && mealsMap[fragmentPosition] != null) {
            f!!.update(mealsMap[fragmentPosition]!!.toTypedArray())
        }
        return super.getItemPosition(fragmentObject)
    }


}