package apps.esampaio.com.comacerto.view.meals.register

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.Menu
import android.view.inputmethod.EditorInfo
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.createDate
import apps.esampaio.com.comacerto.core.extensions.createHour
import apps.esampaio.com.comacerto.view.meals.register.adapter.FeelingsListRecyclerViewAdapter
import apps.esampaio.com.comacerto.view.meals.register.adapter.MealListRecyclerViewAdapter
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import kotlinx.android.synthetic.main.activity_add_new_meal_2.*
import java.text.DateFormat
import java.util.*
import android.widget.ArrayAdapter
import android.widget.SeekBar
import apps.esampaio.com.comacerto.core.entity.Level
import apps.esampaio.com.comacerto.view.meals.register.adapter.ListFoodRecyclerViewAdapter
import kotlinx.android.synthetic.main.layout_view_hunger_level.*


class AddNewMealActivity : AppCompatActivity(), CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener,SeekBar.OnSeekBarChangeListener {
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
        val hour = Calendar.getInstance().createHour(hourOfDay,minute)
        hour_text_view.text = "${hour.asString("HH:mm")}"
    }

    override fun onDateSet(dialog: CalendarDatePickerDialogFragment?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance().createDate(day = dayOfMonth,month = monthOfYear,year = year)
        complete_date_text_view.text = "${date.asString(DateFormat.getDateInstance(DateFormat.LONG))}"
    }

    companion object {
        val FRAG_TAG_DATE_PICKER = "FRAG_TAG_DATE_PICKER"
        val FRAG_TAG_TIME_PICKER = "FRAG_TAG_TIME_PICKER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_meal_2)

        setupFoodsList()
        setupDateTimeDialogs()
        setupMealsList()
        setupFeelingsList()
        setupAutocompleteFoods()
        setupHungerAndSatietySliders()
    }

    private fun setupFoodsList() {
        foods_list_rv.adapter = ListFoodRecyclerViewAdapter(this)
        foods_list_rv.layoutManager = LinearLayoutManager(this)
    }

    private fun addFoodToList(foodName:String){
        (foods_list_rv.adapter as ListFoodRecyclerViewAdapter).addFood(foodName)
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

    private fun setupMealsList(){
        cell_meal.setOnClickListener {
            list_meal_expandable_layout.toggle(true)
        }

        val adapter = MealListRecyclerViewAdapter(this)
        meal_list_rv.adapter = adapter
        meal_list_rv.layoutManager = GridLayoutManager(this,4)
        adapter.onMealSelectedListener = {
            meal_name.text = it.getName(this)
            Handler().postDelayed( {
                list_meal_expandable_layout.collapse()
            },100)
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
            feeling_name.text = it.getName(this)
            Handler().postDelayed( {
                list_feelings_expandable_layout.collapse()
            },100)
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
