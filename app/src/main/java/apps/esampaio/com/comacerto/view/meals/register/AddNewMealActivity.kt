package apps.esampaio.com.comacerto.view.meals.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.*
import apps.esampaio.com.comacerto.core.extensions.appendDate
import apps.esampaio.com.comacerto.core.extensions.appendTime
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.service.meal.MealIteractor
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.core.service.meal.MealService
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.adapter.FeelingsListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.ImageRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.MealListRecyclerViewAdapter
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import kotlinx.android.synthetic.main.activity_add_new_meal.*
import kotlinx.android.synthetic.main.layout_view_hunger_level.*
import java.text.DateFormat
import java.util.*


open class AddNewMealActivity : BaseActivity(), CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener, SeekBar.OnSeekBarChangeListener, MealPresenter {
    companion object {
        val FRAG_TAG_DATE_PICKER = "FRAG_TAG_DATE_PICKER"
        val FRAG_TAG_TIME_PICKER = "FRAG_TAG_TIME_PICKER"
        val MEAL_INTENT_PARAM = "PARAM_MEAL"
        val ADD_FOODS_REQUEST_CODE = 123;

        fun buildIntent(context: Context):Intent{
            val intent = Intent(context, AddNewMealActivity::class.java)
            return intent
        }
        fun buildIntent(context: Context,meal:Meal):Intent{
            val intent = Intent(context, AddNewMealActivity::class.java)
            intent.putExtra(MEAL_INTENT_PARAM,meal)
            return intent
        }
    }

    var meal = Meal()
    lateinit var mealIteractor:MealIteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_meal)

        val receivedMeal = intent?.extras?.getSerializable(MEAL_INTENT_PARAM) as Meal?
        if (receivedMeal != null) {
            this.meal = receivedMeal
        }
        setupDateTimeDialogs()
        setupMealsList()
        setupFeelingsList()
        setupAutocompleteFoods()
        setupHungerAndSatietySliders()
        setTitle(R.string.new_meal)
        mealIteractor = MealService(this)

    }


    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (seekbar?.id == hunger_level_seek_bar.id) {
            hunger_level_text.text = Level.hungerStatusNames[progress]
            meal.hunger.level = progress
        } else {
            satiety_level_text.text = Level.satietyStatusNames[progress]
            meal.satiety.level = progress
        }
    }

    open fun onSaveClick(view: View) {
        saveMeal()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}

    override fun onTimeSet(dialog: RadialTimePickerDialogFragment?, hourOfDay: Int, minute: Int) {
        meal.date = Calendar.getInstance().appendTime(meal.date, hourOfDay, minute)
        updateTimeTextView(meal.date)
    }

    override fun onDateSet(dialog: CalendarDatePickerDialogFragment?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        meal.date = Calendar.getInstance().appendDate(date = meal.date, day = dayOfMonth, month = monthOfYear, year = year)
        updateDateTextView(meal.date)
    }

    override fun onResume() {
        super.onResume()
        updateDateTextView(meal.date)
        updateTimeTextView(meal.date)
        updateFoodsLabel()
        setSelectedFeeling(meal.feeling)
        setSelectedMealType(meal.mealType)
        setHungerAndSatietyLevels(meal.hunger, meal.satiety)
        what_doing_text_view.setText(meal.whatDoing)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_FOODS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val foodsArray = data?.extras?.getSerializable(SelectFoodsActivity.FOODS_LIST_RESULT) as Array<Food>
            meal.foods = foodsArray.asList()
        }
    }

    private fun updateDateTextView(date: Date) {
        complete_date_text_view.text = "${date.asString(DateFormat.getDateInstance(DateFormat.LONG))}"
    }

    private fun updateTimeTextView(hour: Date) {
        hour_text_view.text = "${hour.asString("HH:mm")}"
    }

    private fun updateFoodsLabel() {
        if (meal.foods.isEmpty()) {
            add_foods_text_view.text = null
        } else if ( meal.foods.size == 1){
            add_foods_text_view.text = "${meal.foods.get(0).name}"
        } else if ( meal.foods.size == 2){
            add_foods_text_view.text = "${meal.foods.get(0).name},${meal.foods.get(1).name}"
        }else{
            val quantity = meal.foods.size - 2
            add_foods_text_view.text = "${meal.foods.get(0).name},${meal.foods.get(1).name} ${resources.getQuantityString(R.plurals.and_more_foods,quantity,quantity)}"
        }
    }

    private fun setupHungerAndSatietySliders() {
        hunger_level_seek_bar.setOnSeekBarChangeListener(this)
        satiety_level_seekbar.setOnSeekBarChangeListener(this)
        hunger_level_seek_bar.progress = meal.hunger.level
        satiety_level_seekbar.progress = meal.satiety.level
    }

    private fun setupDateTimeDialogs() {
        complete_date_text_view.setOnClickListener {
            openCalendarDialog()
        }
        hour_text_view.setOnClickListener {
            openHourDialog()
        }
    }

    private fun setupAutocompleteFoods() {
        add_foods_text_view.setOnClickListener {
            val intent = Intent(this, SelectFoodsActivity::class.java)
            intent.putExtra(SelectFoodsActivity.FOODS_LIST_PARAM, meal.foods.toTypedArray())
            startActivityForResult(intent, ADD_FOODS_REQUEST_CODE)
        }
    }

    private fun setSelectedMealType(mealType: MealType) {
        meal.mealType = mealType;
        (meal_list_rv.adapter as ImageRecyclerViewAdapter).selectedItem = (mealType.ordinal - 1)
        meal_name.text = mealType.getName(this)
        Handler().postDelayed({
            list_meal_expandable_layout.collapse()
        }, 100)
    }

    private fun setSelectedFeeling(feeling: Feeling) {
        meal.feeling = feeling;
        (feeling_list_rv.adapter as ImageRecyclerViewAdapter).selectedItem = (feeling.ordinal - 1)
        feeling_name.text = feeling.getName(this)
        Handler().postDelayed({
            list_feelings_expandable_layout.collapse()
        }, 100)
    }

    private fun setHungerAndSatietyLevels(hunger: Level, satiety: Level) {
        hunger_level_text.text = Level.hungerStatusNames[hunger.level]
        hunger_level_seek_bar.progress = hunger.level
        satiety_level_text.text = Level.satietyStatusNames[satiety.level]
        satiety_level_seekbar.progress = satiety.level
    }

    private fun saveMeal() {
        meal.hunger = Level.hunger(hunger_level_seek_bar.progress)
        meal.satiety = Level.satiety(hunger_level_seek_bar.progress)
        meal.whatDoing = what_doing_text_view.text.toString()
        mealIteractor.onSavePressed(meal)
    }

    private fun setupMealsList() {
        cell_meal.setOnClickListener {
            var targetAngle = 0f
            list_meal_expandable_layout.apply {
                if (isExpanded) {
                    collapse(true)
                    targetAngle = 0f
                } else {
                    expand(true)
                    targetAngle = 90f
                }
                meal_expandable_indicator
                        .animate()
                        .rotation(targetAngle)
                        .setDuration(500L)
                        .setInterpolator(LinearInterpolator())
                        .start()
            }
        }
        val adapter = MealListRecyclerViewAdapter(this)
        meal_list_rv.adapter = adapter
        meal_list_rv.layoutManager = GridLayoutManager(this, 4)
        adapter.onMealSelectedListener = {
            setSelectedMealType(it)
        }
    }

    private fun setupFeelingsList() {
        cell_feeling.setOnClickListener {
            var targetAngle = 0f
            list_feelings_expandable_layout.apply {
                if (isExpanded) {
                    collapse(true)
                    targetAngle = 0f
                } else {
                    expand(true)
                    targetAngle = 90f
                }
                feeling_expandable_indicator
                        .animate()
                        .rotation(targetAngle)
                        .setDuration(500L)
                        .setInterpolator(LinearInterpolator())
                        .start()
            }
        }
        val adapter = FeelingsListRecyclerViewAdapter(this)
        feeling_list_rv.adapter = adapter
        feeling_list_rv.layoutManager = GridLayoutManager(this, 4)
        (feeling_list_rv.adapter as FeelingsListRecyclerViewAdapter).notifyDataSetChanged()
        adapter.onFeelingSelectedListener = {
            setSelectedFeeling(it)
        }
    }

    private fun openCalendarDialog() {
        val cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener(AddNewMealActivity@ this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
        cdp.show(supportFragmentManager, FRAG_TAG_DATE_PICKER)
    }

    private fun openHourDialog() {
        val rtpd = RadialTimePickerDialogFragment()
                .setOnTimeSetListener(this@AddNewMealActivity)
        rtpd.show(supportFragmentManager, FRAG_TAG_TIME_PICKER)
    }
}
