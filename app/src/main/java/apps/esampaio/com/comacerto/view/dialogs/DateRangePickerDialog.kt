package apps.esampaio.com.comacerto.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView

import java.util.Calendar
import java.util.Date

import apps.esampaio.com.comacerto.R

class DateRangePickerDialog(context: Context?) : Dialog(context), DateRangeCalendarView.CalendarListener {
    override fun onDateRangeSelected(startDate: Calendar?, endDate: Calendar?) {
        initialDate = startDate?.time
        finalDate = endDate?.time
    }

    override fun onFirstDateSelected(startDate: Calendar?) {

    }

    lateinit var calendarView: DateRangeCalendarView
    lateinit var okButton:Button
    lateinit var cancelButton:Button

    var initialDate: Date? = null
    var finalDate: Date? = null

    interface OnDateRangeSelectedListener{
        fun onDateRangeSelected(initialDate: Date,finalDate: Date)
    }
    var onDateRangeSelectedListener :  OnDateRangeSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.date_range_picker_dialog)
        calendarView = findViewById<DateRangeCalendarView>(R.id.calendar)
        calendarView.setCalendarListener(this)
        okButton = findViewById(R.id.button_ok)
        cancelButton = findViewById(R.id.button_cancel)
        cancelButton.setOnClickListener {
            dismiss()
        }
        okButton.setOnClickListener {
            dateRangeSelected()
        }

        setCancelable(false)
    }

    fun dateRangeSelected(){
        if ( initialDate!= null && finalDate!= null){

            if(onDateRangeSelectedListener != null){
                onDateRangeSelectedListener!!.onDateRangeSelected(initialDate!!, finalDate!!)
            }

            dismiss()
        }
    }
}
