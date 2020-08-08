package apps.esampaio.com.comacerto.view.water

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.appendDate
import apps.esampaio.com.comacerto.core.extensions.appendTime
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.fromFormat
import apps.esampaio.com.comacerto.core.service.water.WaterIteractor
import apps.esampaio.com.comacerto.core.service.water.WaterService
import apps.esampaio.com.comacerto.view.BaseActivity
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import kotlinx.android.synthetic.main.activity_add_water.*
import kotlinx.android.synthetic.main.list_foods_recycler_view_item_2.*
import java.text.DateFormat
import java.util.*

class AddWaterActivity : BaseActivity(), CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {


    companion object {
        val PARAM_ADD_WATER_DATE_SELECTED = "PARAM_ADD_WATER_DATE_SELECTED"
        fun createIntent(context: Context, selectedDate: Date) : Intent {
            val intent = Intent(context,AddWaterActivity::class.java)
            intent.putExtra(PARAM_ADD_WATER_DATE_SELECTED,selectedDate.time)
            return intent
        }
    }
    lateinit var currentDate:Date;

    lateinit var waterIteractor:WaterIteractor

    var cupsQuantity = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_water)
        setTitle(R.string.add_water)

        val currentDateMillis = intent.extras?.getLong(PARAM_ADD_WATER_DATE_SELECTED, -1)
        if(currentDateMillis == null){
            currentDate = Date()
        }else{
            currentDate = Date(currentDateMillis)
        }

        minus_one_button.setOnClickListener {
            if(cupsQuantity>0){
                cupsQuantity--;
                updateCupsQuantity()
            }
        }
        plus_one_button.setOnClickListener {
            cupsQuantity++;
            updateCupsQuantity()
        }
        layout_date.setOnClickListener {
            openCalendarDialog()
        }
        layout_time.setOnClickListener {
            openHourDialog()
        }
        food_name_text_view.text = getString(R.string.whater_quantity)
        food_category_text_view.text = getString(R.string.cups)
        updateCupsQuantity()
        updateDateAndTime()
        waterIteractor = WaterService(this)
    }

    fun onAddWaterClicked(view:View){
        waterIteractor.onWaterSavedClick(Water(currentDate,cupsQuantity))
        finish()
    }
    override fun onDateSet(dialog: CalendarDatePickerDialogFragment?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        currentDate = Calendar.getInstance().appendDate(date = currentDate, day = dayOfMonth, month = monthOfYear, year = year)
        updateDateAndTime()
    }

    override fun onTimeSet(dialog: RadialTimePickerDialogFragment?, hourOfDay: Int, minute: Int) {
        currentDate = Calendar.getInstance().appendTime(currentDate, hourOfDay, minute)
        updateDateAndTime()
    }


    private fun updateCupsQuantity(){
        food_quantity_text_view.text = cupsQuantity.toString()
    }
    private fun updateDateAndTime(){
        complete_date_text_view.text = currentDate.asString(DateFormat.getDateInstance(DateFormat.LONG))
        hour_text_view.text = currentDate.asString("HH:mm")
    }

    private fun openCalendarDialog() {
        val cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener(this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
        cdp.show(supportFragmentManager, AddNewMealActivity.FRAG_TAG_DATE_PICKER)
    }

    private fun openHourDialog() {
        val rtpd = RadialTimePickerDialogFragment()
                .setOnTimeSetListener(this)
        rtpd.show(supportFragmentManager, AddNewMealActivity.FRAG_TAG_TIME_PICKER)
    }
}
