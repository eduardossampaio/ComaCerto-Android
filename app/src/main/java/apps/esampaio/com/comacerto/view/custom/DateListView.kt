package apps.esampaio.com.comacerto.view.custom

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher
import apps.esampaio.com.comacerto.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import apps.esampaio.com.comacerto.core.extensions.*

@Deprecated("Substituido")
class DateListView : RelativeLayout {
    companion object {
        val weekDayFormatter = SimpleDateFormat("dd")
        val weekDayNameFormatter = SimpleDateFormat("EE")
        val completeDayFormatter = DateFormat.getDateInstance(DateFormat.LONG)!!
    }
    interface DayItemSelectedListener{
        fun daySelected(day:Date)
    }

    var onDayItemSelectedListener : DayItemSelectedListener? = null
    //views
    var dayViews = mutableListOf<DayViewHolder>()
    lateinit var selectedDayTextView: TextSwitcher

    //fields
    var currentWeek = 0
    var selectedDay:Date = Date(System.currentTimeMillis())
        set(value) {
            field = value
            selectedDayTextView!!.setText( completeDayFormatter.format(value) )
            refreshViews()
        }
    var currentDay = Date(System.currentTimeMillis())

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
        setupSelectedDayTextView()
        dayViews.add(0, DayViewHolder(findViewById(R.id.sunday_view),0))
        dayViews.add(1, DayViewHolder(findViewById(R.id.monday_view),1))
        dayViews.add(2, DayViewHolder(findViewById(R.id.tuesday_view),2))
        dayViews.add(3, DayViewHolder(findViewById(R.id.wednesday_view),3))
        dayViews.add(4, DayViewHolder(findViewById(R.id.thursday_view),4))
        dayViews.add(5, DayViewHolder(findViewById(R.id.friday_view),5))
        dayViews.add(6, DayViewHolder(findViewById(R.id.saturday_view),6))

        refreshViews()
    }

    private fun setupSelectedDayTextView(){
        selectedDayTextView = findViewById(R.id.selected_day);
        selectedDayTextView.setFactory(object : ViewSwitcher.ViewFactory{
            override fun makeView(): View {
                val t = TextView(context)
                t.gravity = Gravity.CENTER
                return t
            }
        })
        val aninIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        val aninOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
        aninIn.duration = 200
        aninOut.duration = 200
        selectedDayTextView.inAnimation = aninIn
        selectedDayTextView.outAnimation = aninOut
        selectedDayTextView.setText( completeDayFormatter.format(Date()) )
    }

    fun refreshViews(){
        val selectedDayWeek = selectedDay.getWeek()
        val sameWeek = (selectedDayWeek == currentWeek);
        if ( sameWeek){
            setSelectedView()
        }else{
            updateWeek(selectedDayWeek)
        }
    }

    fun updateViews(){
        val firstDayOfWeek = selectedDay.getFirsDayOfWeek()
        val calendar = Calendar.getInstance()
        calendar.time = firstDayOfWeek
        for (i in 0..6) {
            dayViews[i].setDate(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
    fun setSelectedView(){
        for (i in 0..6) {
            if(selectedDay.sameDay(dayViews[i].itemDate!!)){
                dayViews[i].setSelected(true)
            }else{
                dayViews[i].setSelected(false)
            }
        }
    }

    fun updateWeek(newWeek: Int){
        currentWeek = newWeek
        updateViews()
        setSelectedView()
        //refresh days
    }

    fun selectNextDay(){
        val calendar = Calendar.getInstance()
        calendar.time = selectedDay
        calendar.add(Calendar.DAY_OF_YEAR,-1)
        selectedDay = calendar.time
        refreshViews()
    }

    fun selectPreviousDay(){
        val calendar = Calendar.getInstance()
        calendar.time = selectedDay
        calendar.add(Calendar.DAY_OF_YEAR,1)
        selectedDay = calendar.time
        refreshViews()
    }



    inner class DayViewHolder(val view: View,val positionInView:Int) : View.OnClickListener {

        private val subview:View
        private val dayItem: TextView
        private val weekDayItem: TextView
        private var enabled = true;
        var itemDate:Date? = null

        init {
            view.setOnClickListener(this)
            subview = view.findViewById(R.id.subview)
            dayItem = view.findViewById(R.id.day_item)
            weekDayItem = view.findViewById(R.id.week_day_item)
        }

        fun setDate(date: Date) {
            dayItem.text = weekDayFormatter.format(date)
            weekDayItem.text = weekDayNameFormatter.format(date)
            this.itemDate = date
            this.enabled = isEnabled(date)
        }

        private fun isEnabled(date: Date): Boolean{

            return (date.time <= currentDay.time) && date.getDateYear() >= currentDay.getDateYear()
        }
        fun setSelected(selected:Boolean){
            if (!enabled){
                dayItem.background = ContextCompat.getDrawable(view.context,android.R.color.transparent)
                dayItem.setTextColor(ContextCompat.getColor(view.context,R.color.date_disabled))
                weekDayItem.setTextColor(ContextCompat.getColor(view.context,R.color.date_disabled))
                return
            }
            if ( selected ){
                dayItem.background = ContextCompat.getDrawable(view.context,R.drawable.circle_drawable)
                weekDayItem.setTextColor(ContextCompat.getColor(view.context,R.color.primary))
                dayItem.setTextColor(ContextCompat.getColor(view.context,R.color.white))
            }else{
                dayItem.background = ContextCompat.getDrawable(view.context,android.R.color.transparent)
                weekDayItem.setTextColor(ContextCompat.getColor(view.context,R.color.primary))
                dayItem.setTextColor(ContextCompat.getColor(view.context,R.color.primary))
            }
        }

        override fun onClick(p0: View?) {
            if (itemDate != null && enabled) {
                selectedDay = itemDate!!
                onDayItemSelectedListener?.daySelected(selectedDay)
            }
        }
    }
}
