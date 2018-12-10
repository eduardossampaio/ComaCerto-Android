package apps.esampaio.com.comacerto.view.meals.list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.*
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.core.service.meal.MealService
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.custom.DateListView
import apps.esampaio.com.comacerto.view.meals.list.adapter.ListMealPageViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import apps.esampaio.com.comacerto.view.water.AddWaterActivity
import kotlinx.android.synthetic.main.fragment_list_meals_2.*
import java.util.*

class ListMealsFragment : BaseFragment(), ViewPager.OnPageChangeListener, DateListView.DayItemSelectedListener,MealPresenter {

    var currentItemPosition = 0
    var mealIterator = MealService(this)
    lateinit var pageViewAdapter : ListMealPageViewAdapter
    var lastSelectedDay = Date()
    var lastRetrievedMeals:List<Meal> = emptyList()

    init {
        setHasOptionsMenu(true)
    }

    fun onNewMealClicked(){
        val templateMeal = Meal()
        templateMeal.date = lastSelectedDay
        templateMeal.mealType = getNextMealType()
        val intent = AddNewMealActivity.buildIntent(context!!,templateMeal)
        startActivity(intent)
    }
    fun onAddWaterClicked(){
        val intent = Intent(context,AddWaterActivity::class.java)
        startActivity(intent)
    }

    override fun updateMealList(meals: List<Meal>) {
        this.lastRetrievedMeals = meals
        pageViewAdapter.updateData(currentItemPosition,meals)
    }

    override fun updateWaterList(waterList: List<Water>) {
        Handler(Looper.getMainLooper()).post {
            pageViewAdapter.updateWaterList(waterList)
        }
    }

    override fun daySelected(day: Date) {
        val position = day.dayOfYear()
        Log.d("ListMealsFragment","day selected from header. day:${day} position: ${position}")
        currentItemPosition = position
        daily_meal_view_pager.currentItem = currentItemPosition
        newDaySelected(day)
    }
    private fun getNextMealType() : MealType{

        for (mealType in MealType.values()){
            if(mealType == MealType.None){
                continue
            }
            var found = false
            for(meal in lastRetrievedMeals){
                if(meal.mealType == mealType){
                    found = true
                }
            }
            if ( !found){
                return mealType
            }
        }

        return  MealType.Breakfast
    }
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(pageIndex: Int, position1: Float, position2: Int) {

    }

    fun newDaySelected(day: Date){
        mealIterator.dateSelected(day)
        this.lastSelectedDay = day
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
        return inflater.inflate(R.layout.fragment_list_meals_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listOfMeal = mutableListOf<Meal>()
        this.pageViewAdapter = ListMealPageViewAdapter(context!!, daily_meal_view_pager, listOfMeal)
        daily_meal_view_pager.adapter = pageViewAdapter
        daily_meal_view_pager.currentItem = pageViewAdapter.count -1
        daily_meal_view_pager.addOnPageChangeListener(this)
        currentItemPosition = daily_meal_view_pager.currentItem
        navigation_header.onDayItemSelectedListener = this
        onPageSelected(daily_meal_view_pager.currentItem)
        inflateFab()
    }

    private fun inflateFab(){
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

    override fun onResume() {
        super.onResume()
        onPageSelected(daily_meal_view_pager.currentItem)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMealsFragment().apply {

        }
    }
}
