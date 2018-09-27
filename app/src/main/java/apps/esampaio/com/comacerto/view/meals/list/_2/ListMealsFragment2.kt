package apps.esampaio.com.comacerto.view.meals.list._2

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.*
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.extensions.dayOfYear
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.core.service.meal.MealService
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.custom.DateListView
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import kotlinx.android.synthetic.main.fragment_list_meals_2.*
import java.util.*


class ListMealsFragment2 : BaseFragment(), ViewPager.OnPageChangeListener, DateListView.DayItemSelectedListener,MealPresenter {

    var currentItemPosition = 0
    var mealIterator = MealService(this)
    lateinit var adapter : ListMealAdapter

    init {
        setHasOptionsMenu(true)
    }

    fun onNewMealClicked(){
        val intent = Intent(context, AddNewMealActivity::class.java)
            startActivity(intent)
    }

    override fun updateMealList(meals: List<Meal>) {
        adapter.updateData(currentItemPosition,meals)

    }

    override fun daySelected(day: Date) {
        val position = day.dayOfYear()
        Log.d("ListMealsFragment","day selected from header. day:${day} position: ${position}")
        currentItemPosition = position
        daily_meal_view_pager.currentItem = currentItemPosition
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
        return inflater.inflate(R.layout.fragment_list_meals_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listOfMeal = mutableListOf<Meal>()
        this.adapter =  ListMealAdapter(context!!,daily_meal_view_pager,listOfMeal)
        daily_meal_view_pager.adapter = adapter
        daily_meal_view_pager.currentItem = adapter.count -1
        daily_meal_view_pager.addOnPageChangeListener(this)
        currentItemPosition = daily_meal_view_pager.currentItem
        navigation_header.onDayItemSelectedListener = this
        add_new_meal_button.setOnClickListener { onNewMealClicked() }
    }

    override fun onResume() {
        super.onResume()
        onPageSelected(daily_meal_view_pager.currentItem)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMealsFragment2().apply {

        }
    }
}
