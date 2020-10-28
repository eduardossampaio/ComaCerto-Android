package apps.esampaio.com.comacerto.view.meals.list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.differenceInDays
import apps.esampaio.com.comacerto.core.extensions.subtractDay
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.core.service.meal.MealService
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.meals.list.adater.ListDailyMealRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import apps.esampaio.com.comacerto.view.water.AddWaterActivity
import com.huanhailiuxin.coolviewpager.CoolViewPager
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.HorizontalCalendarListener
import kotlinx.android.synthetic.main.fragment_list_meals_2.*
import java.util.*
import apps.esampaio.com.comacerto.core.extensions.beginOfMonth;

class ListMealsFragment : BaseFragment(), MealPresenter {

    private var currentItemPosition = 0;
    lateinit var horizontalCalendar: HorizontalCalendar
    lateinit var mealService: MealService;
    lateinit var initialDate: Date;
    lateinit var finalDate: Date;
    lateinit var pageAdapter: PageViewAdapter;
    private var fromCalendarListener = false;
    var lastSelectedDay = Date(System.currentTimeMillis())

    inner class PageViewAdapter(val context: Context, val pageView: CoolViewPager, private var itemCount: Int = 365) : PagerAdapter() {

        var meals = mutableMapOf<Int,List<Meal>?>()
        var water = mutableMapOf<Int,List<Water>?>()
        var viewHolders = mutableMapOf<Int,View>()

        private val currentDate = Date(System.currentTimeMillis());

        override fun isViewFromObject(view: View, theObject: Any): Boolean {
            return theObject == view
        }

        override fun getCount(): Int {
            return itemCount;
        }

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(context).inflate(R.layout.fragment_daily_meals, collection, false)
            view.tag = position
            collection.addView(view)
            viewHolders[position] = view
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

        private fun  updateViews(view:View, mealsOfDay:List<Meal>?, waterOfDay:List<Water>?){
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


        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        fun selectItem(date: Date) {

           val itemPosition = getItemPosition(date)
            currentItemPosition = itemPosition;
            setCurrentItemQuietly(itemPosition -1);
        }

        fun getItemPosition(date: Date) : Int{
            val difference = currentDate.differenceInDays(date);
            val itemPosition = (itemCount) - difference;
            return itemPosition;
        }

        fun getSelectedDate(): Date {
          val differenceInDays =  (itemCount -1) - pageView.currentItem;
            val date = currentDate.subtractDay(differenceInDays);
            return date;
        }


    }

    fun setCurrentItemQuietly(itemPosition: Int){
        daily_meal_view_pager.clearOnPageChangeListeners();
        daily_meal_view_pager.setCurrentItem(itemPosition , true);

        Handler().postDelayed({
            daily_meal_view_pager.addOnPageChangeListener(pageSelected);
        },200);

    }

    private val pageSelected = object : CoolViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            if(CoolViewPager.SCROLL_STATE_IDLE == state){
                val selectedDate = pageAdapter.getSelectedDate();
                Log.d("OnPageChangeListener", "selectedDate: $selectedDate");
                horizontalCalendar.selectDate(selectedDate, false);
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {

        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ListMealsFragment().apply {

        }
    }


    private fun onNewMealClicked() {
        val templateMeal = Meal()
        templateMeal.date = lastSelectedDay
        val intent = AddNewMealActivity.buildIntent(context!!, templateMeal)
        startActivity(intent)
    }

    private fun onAddWaterClicked() {
        val intent = AddWaterActivity.createIntent(context!!, lastSelectedDay);
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_meals_2, container, false)
    }

    override fun onResume() {
        super.onResume()
        mealService.dateSelected(lastSelectedDay)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mealService = MealService(this)


        var startDateCalendar = Calendar.getInstance()
        startDateCalendar.time = Date(System.currentTimeMillis());
        startDateCalendar.add(Calendar.MONTH, -2)

        this.initialDate = startDateCalendar.time.beginOfMonth();
        this.finalDate = Date(System.currentTimeMillis());
        val totalDaysItems = finalDate.differenceInDays(initialDate);

        this.pageAdapter = PageViewAdapter(context!!, daily_meal_view_pager, totalDaysItems);
        daily_meal_view_pager.addOnPageChangeListener(pageSelected)

        setupHorizontalCalendar(view);

        daily_meal_view_pager.adapter = this.pageAdapter;

        this.currentItemPosition = totalDaysItems -1
        daily_meal_view_pager.currentItem = totalDaysItems;

        inflateFab()

        this.mealService.dateSelected(lastSelectedDay);

    }

    override fun updateMealAndWaterList(date: Date,meals: List<Meal>, water: List<Water>) {
        Handler(Looper.getMainLooper()).post {

            val itemPos = pageAdapter.getItemPosition(date)
            pageAdapter.updateDataAt(itemPos-1,meals,water)
        }
    }

    private fun setupHorizontalCalendar(view: View) {


        this.horizontalCalendar = HorizontalCalendar.Builder(view, R.id.calendarView)
                .startDate(initialDate)
                .endDate(finalDate)
                .build()
        horizontalCalendar.setElevation(1f)

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Date?, position: Int) {
                if (date != null) {
                    fromCalendarListener = true;
                    lastSelectedDay = date;
                    pageAdapter.selectItem(date);
                    mealService.dateSelected(lastSelectedDay);

                }
            }
        }
        horizontalCalendar.selectDate(lastSelectedDay, false);
    }

    private fun inflateFab() {

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
}