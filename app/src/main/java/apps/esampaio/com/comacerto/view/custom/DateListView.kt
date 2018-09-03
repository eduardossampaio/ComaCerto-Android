package apps.esampaio.com.comacerto.view.custom

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateListView : RelativeLayout {
    companion object {
        val weekDayFormatter = SimpleDateFormat("dd")
        val weekDayNameFormatter = SimpleDateFormat("EE")
        val completeDayFormatter = DateFormat.getDateInstance(DateFormat.LONG)
    }
    interface DayItemSelectedListener{
        fun daySelected(day:Date,position: Int)
    }

    inner class DayViewHolder(val view: View,val positionInView:Int) : View.OnClickListener {

        private val subview:View
        private val dayItem: TextView
        private val weekDayItem: TextView
        var date:Date? = null

        init {
            view.setOnClickListener(this)
            subview = view.findViewById(R.id.subview)
            dayItem = view.findViewById(R.id.day_item)
            weekDayItem = view.findViewById(R.id.week_day_item)
        }

        fun setSelectedDate(date: Date) {
            dayItem.text = weekDayFormatter.format(date)
            weekDayItem.text = weekDayNameFormatter.format(date)
            this.date = date
        }

        fun setSelected(selected:Boolean){
            if ( selected ){
                subview.background = ContextCompat.getDrawable(view.context,R.drawable.circle_drawable)
                dayItem.setTextColor(ContextCompat.getColor(view.context,R.color.white))
                weekDayItem.setTextColor(ContextCompat.getColor(view.context,R.color.white))
            }else{
                subview.background = ContextCompat.getDrawable(view.context,android.R.color.transparent)
                dayItem.setTextColor(ContextCompat.getColor(view.context,R.color.primary))
                weekDayItem.setTextColor(ContextCompat.getColor(view.context,R.color.primary))
            }
        }

        override fun onClick(p0: View?) {
            updateSelectedDayItem(this)
            onDayItemSelectedListener?.daySelected(this.date!!,getCurrentSelectedIndex())
        }
    }

    var days = mutableListOf<DayViewHolder>()
    var selectedDayTextView: TextView? = null
    var selectedDayView: DayViewHolder? = null
    var currentWeek = 0
    var onDayItemSelectedListener : DayItemSelectedListener? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView()
    }

    fun initView() {
        inflate(getContext(), R.layout.date_list_view, this);
        selectedDayTextView = findViewById(R.id.selected_day);

        days.add(0, DayViewHolder(findViewById(R.id.sunday_view),0))
        days.add(1, DayViewHolder(findViewById(R.id.monday_view),1))
        days.add(2, DayViewHolder(findViewById(R.id.tuesday_view),2))
        days.add(3, DayViewHolder(findViewById(R.id.wednesday_view),3))
        days.add(4, DayViewHolder(findViewById(R.id.thursday_view),4))
        days.add(5, DayViewHolder(findViewById(R.id.friday_view),5))
        days.add(6, DayViewHolder(findViewById(R.id.saturday_view),6))
        currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
        setupDays()
        updateSelectedDayItem(0)
    }

    private fun setupDays() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.WEEK_OF_YEAR, currentWeek)

        while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.firstDayOfWeek) {
            calendar.add(Calendar.DATE, -1);
        }

        for (i in 0..6) {
            days[i].setSelectedDate(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

    }
    fun updateSelectedDayItem(newSelectedDayPosition: Int){
        updateSelectedDayItem(days[newSelectedDayPosition])
    }
    fun updateSelectedDayItem(newSelectedDay: DayViewHolder){

        if (selectedDayView != null) {
            selectedDayView!!.setSelected(false)
        }
        newSelectedDay.setSelected(true)
        this.selectedDayView = newSelectedDay
        displaySelectedDayText()
    }

    private fun displaySelectedDayText(){
        if (selectedDayTextView != null && this.selectedDayView!!.date != null) {
            val formattedCurrentDay = completeDayFormatter.format(selectedDayView!!.date)
            selectedDayTextView!!.text = formattedCurrentDay

        }
    }

    fun selectNextDay(){
        var currentSelected = getCurrentSelectedIndex()
        currentSelected--
        if( currentSelected >= 0){
            updateSelectedDayItem(currentSelected)
        }
    }

    fun selectPreviousDay(){
        var currentSelected = getCurrentSelectedIndex()
        currentSelected++
        if( currentSelected < days.size){
            updateSelectedDayItem(currentSelected)
        }
    }

    private fun getCurrentSelectedIndex() : Int{
        if (selectedDayView != null){
            return selectedDayView!!.positionInView
        }
        return 0;
    }
}