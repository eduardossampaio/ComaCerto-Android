package apps.esampaio.com.comacerto.view.meals.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.view.PagerAdapter
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
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.core.service.meal.MealService
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.meals.list.adater2.ListDailyMealRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import apps.esampaio.com.comacerto.view.water.AddWaterActivity
import kotlinx.android.synthetic.main.fragment_daily_meals.*
import kotlinx.android.synthetic.main.fragment_list_meals_2.*
import java.util.*


class ListMealFragment2 : BaseFragment(), ViewPager.OnPageChangeListener,MealPresenter {

    var currentItemPosition = 0;
    lateinit var adapter:PageViewAdapter
    lateinit var mealService:MealService
    var lastSelectedDay = Date(System.currentTimeMillis())

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(pageIndex: Int, position1: Float, position2: Int) {

    }

    override fun onPageSelected(pageIndex: Int) {
        val newPosition = pageIndex;
        if (newPosition < currentItemPosition) {
            navigation_header.selectNextDay()
        } else if (newPosition > currentItemPosition) {
            navigation_header.selectPreviousDay()
        }
        currentItemPosition = newPosition
        newDaySelected(navigation_header.selectedDay)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMealFragment2().apply {

        }
    }

    class PageViewAdapter(val context: Context) : PagerAdapter() {

        var meals = mutableMapOf<Int,List<Meal>?>()
        var water = mutableMapOf<Int,List<Water>?>()

        var viewHolders = mutableMapOf<Int,View>()

        override fun isViewFromObject(view: View, theObject: Any): Boolean {
            return theObject == view
        }

        override fun getCount(): Int {
            return Date(System.currentTimeMillis()).dayOfYear()
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(context).inflate(R.layout.fragment_daily_meals, collection, false)
            view.tag = position
            collection.addView(view)
            viewHolders.put(position,view)
            updateDataAt(position,meals[position],water[position])
            return view
        }


        fun updateDataAt(position:Int, meals: List<Meal>?, water: List<Water>?){
            this.meals.put(position , meals);
            this.water[position] = water;
            var view = viewHolders.get(position)
            if (view!= null) {
                updateViews(view, meals,water);
            }
        }


        fun  updateViews(view:View,mealsOfDay:List<Meal>?,waterOfDay:List<Water>?){
            val mealListRecyclerView = view.findViewById<RecyclerView>(R.id.daily_meals_recycler_view)
            val noMealTextView = view.findViewById<TextView>(R.id.no_meals_registered_text_view)

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

    private fun newDaySelected(day:Date){
        mealService.dateSelected(day)
        this.lastSelectedDay = day;
    }

    override fun updateMealAndWaterList(meals: List<Meal>, water: List<Water>) {
        Handler(Looper.getMainLooper()).post {
            adapter.updateDataAt(currentItemPosition,meals,water)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_meals_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mealService = MealService(this)
        this.adapter = PageViewAdapter(context!!)
        daily_meal_view_pager.adapter = adapter
        daily_meal_view_pager.currentItem = adapter.count - 1
        daily_meal_view_pager.addOnPageChangeListener(this)
        this.currentItemPosition = daily_meal_view_pager.currentItem

        inflateFab()
    }


    override fun onResume() {
        super.onResume()
        newDaySelected(lastSelectedDay)
    }

    private fun inflateFab() {
        //inflate fab
        speedDial.inflate(R.menu.list_food_menu);
        speedDial.setOnActionSelectedListener { speedDialActionItem ->
            when (speedDialActionItem.id) {
                R.id.add_new_meal -> {
                    onNewMealClicked()
                    false // true to keep the Speed Dial open
                }
                R.id.add_water -> {
                    onAddWaterClicked()
                    false
                }
                else -> false
            }
        }
    }

    fun onNewMealClicked() {
        val templateMeal = Meal()
        templateMeal.date = lastSelectedDay
//        templateMeal.mealType = getNextMealType()
        val intent = AddNewMealActivity.buildIntent(context!!, templateMeal)
        startActivity(intent)
    }

    fun onAddWaterClicked() {
        val intent = Intent(context, AddWaterActivity::class.java)
        startActivity(intent)
    }
}
