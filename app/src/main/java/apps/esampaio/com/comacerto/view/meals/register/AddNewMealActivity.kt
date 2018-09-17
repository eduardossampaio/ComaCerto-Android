package apps.esampaio.com.comacerto.view.meals.register

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.SeekBar
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.*
import apps.esampaio.com.comacerto.core.extensions.appendDate
import apps.esampaio.com.comacerto.core.extensions.appendTime
import apps.esampaio.com.comacerto.core.extensions.asString

import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.adapter.FeelingsListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.ListFoodRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.MealListRecyclerViewAdapter
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import kotlinx.android.synthetic.main.activity_add_new_meal_2.*
import kotlinx.android.synthetic.main.layout_view_hunger_level.*
import java.text.DateFormat
import apps.esampaio.com.comacerto.core.service.meal.MealService
import java.util.*


open class AddNewMealActivity : BaseActivity(), CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener,SeekBar.OnSeekBarChangeListener,MealPresenter {
    override fun updateMealList(meals: List<Meal>) {

    }

    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
        if ( seekbar?.id == hunger_level_seek_bar.id){
            hunger_level_text.text = Level.hungerStatusNames[progress]
        }else{
            satiety_level_text.text = Level.satietyStatusNames[progress]
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}

    override fun onTimeSet(dialog: RadialTimePickerDialogFragment?, hourOfDay: Int, minute: Int) {
        meal.date = Calendar.getInstance().appendTime(meal.date,hourOfDay,minute)
        updateTimeTextView(meal.date)
    }

    override fun onDateSet(dialog: CalendarDatePickerDialogFragment?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        meal.date = Calendar.getInstance().appendDate(date = meal.date,day = dayOfMonth,month = monthOfYear,year = year)
        updateDateTextView( meal.date)
    }

    companion object {
        val FRAG_TAG_DATE_PICKER = "FRAG_TAG_DATE_PICKER"
        val FRAG_TAG_TIME_PICKER = "FRAG_TAG_TIME_PICKER"
        val MEAL_INTENT_PARAM    = "PARAM_MEAL"
    }

    var meal = Meal()
    val mealInteractor = MealService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_meal_2)

        setupFoodsList()
        setupDateTimeDialogs()
        setupMealsList()
        setupFeelingsList()
        setupAutocompleteFoods()
        setupHungerAndSatietySliders()

        updateDateTextView(meal.date)
        updateTimeTextView(meal.date)
        setSelectedFeeling(Feeling.Natural)
        setSelectedMealType(MealType.Breakfast)

        val receivedMeal = intent?.extras?.getSerializable(MEAL_INTENT_PARAM) as Meal?
        if ( receivedMeal != null){
            this.meal = receivedMeal
        }
    }
    private fun updateDateTextView(date:Date){
        complete_date_text_view.text = "${date.asString(DateFormat.getDateInstance(DateFormat.LONG))}"
    }
    private fun updateTimeTextView(hour:Date){
        hour_text_view.text = "${hour.asString("HH:mm")}"
    }
    private fun setupFoodsList() {
        foods_list_rv.adapter = ListFoodRecyclerViewAdapter(this)
        foods_list_rv.layoutManager = LinearLayoutManager(this)
    }

    private fun addFoodToList(foodName:String){
        (foods_list_rv.adapter as ListFoodRecyclerViewAdapter).addFood(foodName)
    }

    private fun getFoods() : List<Food> {
        return (foods_list_rv.adapter as ListFoodRecyclerViewAdapter).foodsList
    }

    private fun setupHungerAndSatietySliders() {
        hunger_level_seek_bar.setOnSeekBarChangeListener(this)
        satiety_level_seekbar.setOnSeekBarChangeListener(this)
        hunger_level_seek_bar.progress = 0
        satiety_level_seekbar.progress = 0
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
        val foods = listOf("Arroz","ArrFeijão","ArrCarne","ArrBatata","ArrOvo")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, foods)
        add_foods_edit_text.setAdapter(adapter)

        add_foods_edit_text.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addFoodToList(add_foods_edit_text.text.toString())
                add_foods_edit_text.setText("")
            }
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean {
        menuInflater.inflate(R.menu.add_new_meal_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.save_meal_menu_item){
          saveMeal()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSelectedMealType(mealType: MealType){
        meal.mealType = mealType;
        meal_name.text = mealType.getName(this)
        Handler().postDelayed( {
            list_meal_expandable_layout.collapse()
        },100)
    }
    private fun setSelectedFeeling(feeling: Feeling){
        meal.feeling = feeling;
        feeling_name.text = feeling.getName(this)
        Handler().postDelayed( {
            list_feelings_expandable_layout.collapse()
        },100)
    }

    private fun saveMeal(){
        meal.foods = getFoods()
        meal.hunger = Level.hunger( hunger_level_seek_bar.progress)
        meal.satiety = Level.satiety( hunger_level_seek_bar.progress)
        meal.whatDoing = what_doing_text_view.text.toString()
        mealInteractor.onSavePressed(meal)
        finish()
    }

    private fun setupMealsList(){
        cell_meal.setOnClickListener {
            list_meal_expandable_layout.toggle(true)
        }

        val adapter = MealListRecyclerViewAdapter(this)
        meal_list_rv.adapter = adapter
        meal_list_rv.layoutManager = GridLayoutManager(this,4)
        adapter.onMealSelectedListener = {
            setSelectedMealType(it)
        }
    }

    private fun setupFeelingsList(){
        cell_feeling.setOnClickListener {
            list_feelings_expandable_layout.toggle()
        }
        val adapter = FeelingsListRecyclerViewAdapter(this)
        feeling_list_rv.adapter = adapter
        feeling_list_rv.layoutManager = GridLayoutManager(this,4)
        (feeling_list_rv.adapter as FeelingsListRecyclerViewAdapter).notifyDataSetChanged()
        adapter.onFeelingSelectedListener = {
           setSelectedFeeling(it)
        }
    }

    private fun openCalendarDialog(){
        val cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener(AddNewMealActivity@this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
        cdp.show(supportFragmentManager, FRAG_TAG_DATE_PICKER)
    }

    private fun openHourDialog(){
        val rtpd = RadialTimePickerDialogFragment()
                .setOnTimeSetListener(this@AddNewMealActivity)
        rtpd.show(supportFragmentManager, FRAG_TAG_TIME_PICKER)
    }
}
