package apps.esampaio.com.comacerto.view.meals.register

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.KeyEvent
import android.view.Menu
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




class AddNewMealActivity : AppCompatActivity(), CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {
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

        cell_meal.setOnClickListener {
            list_meal_expandable_layout.toggle(true)
        }
        cell_feeling.setOnClickListener {
            list_feelings_expandable_layout.toggle()
        }
        complete_date_text_view.setOnClickListener {
            openCalendarDialog()
        }
        hour_text_view.setOnClickListener {
            openHourDialog()
        }
        setupMealsList()
        setupFeelingsList()
        setupAutocompleteFoods()
    }

    private fun setupAutocompleteFoods() {
        val foods = listOf("Arroz","ArrFeijão","ArrCarne","ArrBatata","ArrOvo")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, foods)
        add_foods_edit_text.setAdapter(adapter)
        add_foods_edit_text.setOnItemClickListener({ adapter,view,position,id ->

        })
        add_foods_edit_text.setOnKeyListener({view,keyCode,event ->
            if(event.action == KeyEvent.ACTION_UP){
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    add_foods_edit_text.setText("")
                }
            }
            true
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean {
        menuInflater.inflate(R.menu.add_new_meal_menu,menu)
        return true
    }

    private fun setupMealsList(){
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
