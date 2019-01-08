package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.view.meals.list.adater2.ListDailyMealRecyclerViewAdapter
import com.asksira.loopingviewpager.LoopingPagerAdapter
import java.util.*

class ListMealPageViewAdapter(context: Context, private val viewPager: ViewPager, itemList: List<Meal>) : LoopingPagerAdapter<Meal>(context, itemList, false) {
    var itemCount = 0
    var allMeals = mutableMapOf<Int,List<Meal>>()
    var allWaters = mutableMapOf<Int,List<Water>>()

    init {
        itemCount = Date(System.currentTimeMillis()).dayOfYear() + 1
    }
    override fun getCount(): Int {
        return itemCount;
    }
    override fun inflateView(viewType: Int, container: ViewGroup?, listPosition: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_daily_meals, container, false)
    }

    fun updateData(position:Int,mealList: List<Meal>,waterList: List<Water>){
        allMeals[position] = mealList
        allWaters[position] = waterList
//        updateCurrent(position)
    }

    fun updateCurrent(position: Int){
        for (i in 0 until viewPager.getChildCount()) {
            val childAt = this.viewPager.getChildAt(i)
            if (childAt.tag as Int == viewPager.getCurrentItem()) {
                bindView(childAt,position,getItemViewType(position))
            }
        }

    }


    override fun bindView(convertView: View?, listPosition: Int, viewType: Int) {
        convertView?.tag = listPosition
        val mealListRecyclerView = convertView?.findViewById<RecyclerView>(R.id.daily_meals_recycler_view)
        val noMealTextView = convertView?.findViewById<TextView>(R.id.no_meals_registered_text_view)

        val mealsOfDay = allMeals[listPosition]
        val waterOfDay = allWaters[listPosition]

        val empty = (  mealsOfDay  == null || mealsOfDay.isEmpty() ) && ( waterOfDay  == null || waterOfDay.isEmpty())
        if ( empty){
            mealListRecyclerView?.visibility = View.GONE
            noMealTextView?.visibility = View.VISIBLE
        }else {
            mealListRecyclerView?.visibility = View.VISIBLE
            noMealTextView?.visibility = View.GONE

//            var listAdapter = mealListRecyclerView?.adapter as ListDailyMealRecyclerViewAdapter?
//            if ( listAdapter == null) {
                mealListRecyclerView?.adapter = ListDailyMealRecyclerViewAdapter(context, mealsOfDay!!, waterOfDay!!)
                mealListRecyclerView?.layoutManager = LinearLayoutManager(context!!)
//            }else{
//                listAdapter.updateItems(mealsOfDay!!,waterOfDay!!)
//                listAdapter.notifyDataSetChanged()
//
//            }

        }
    }

}