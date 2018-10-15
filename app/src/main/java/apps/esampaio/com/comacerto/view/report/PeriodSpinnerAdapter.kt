package apps.esampaio.com.comacerto.view.report

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import apps.esampaio.com.comacerto.R
import android.widget.AdapterView
import apps.esampaio.com.comacerto.core.extensions.*
import java.util.*


class PeriodSpinnerAdapter(context: Context) : ArrayAdapter<Period>(context, 0, buildItems(context)) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return  createItemView(position, convertView, parent,true)
    }


    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup,isSelectedView:Boolean = false): View {
        val view = LayoutInflater.from(context).inflate(R.layout.report_period_spinner_adapter, parent, false)
        val periodName = view.findViewById<TextView>(R.id.period_name)
        val periodRange = view.findViewById<TextView>(R.id.period_range)

        val period = getItem(position) as Period

        periodName.text = period.displayText
        if ( period.initialDate == null && period.finalDate == null) {
            periodRange.visibility = View.GONE
        }else{
            periodRange.visibility = View.VISIBLE
            periodRange.text = period.getPeriodRangeDisplayText()
        }

        if ( isSelectedView ){
            periodName.setTextColor(Color.WHITE)
            periodRange.setTextColor(Color.WHITE)
            if ( position == count-1 ){
                periodName.text = context.getString(R.string.period_custom)
            }
        }
        return view
    }

    fun updateCustomPeriodDate(initialDate: Date,finalDate: Date){
        val lastPosition = count - 1
        val customPeriod = getItem(lastPosition) as Period
        customPeriod.initialDate = initialDate
        customPeriod.finalDate = finalDate
        notifyDataSetChanged()
    }

    companion object {
        fun buildItems(context: Context): Array<Period> {
            val firstDayOfMonth = Date().beginOfMonth()
            val firstDayOWeek = Date().getFirsDayOfWeek()
            val lastDayOfWeek = Date().getLastDayOfWeek()
            val middleOfMonth = Calendar.getInstance().appendDate(Date(), day = 15)
            val lastDayOfMonth = Date().endOfMonth()

            val lastMonth =  Calendar.getInstance().lastMonth(Date())
            val firstDayOfLastMonth = lastMonth.beginOfMonth();
            val lastDayOfLastMonth = lastMonth.endOfMonth();
            return arrayOf(
                    Period(context.getString(R.string.period_weekly),firstDayOWeek,lastDayOfWeek),
                    Period(context.getString(R.string.period_biweekly_1),firstDayOfMonth,middleOfMonth),
                    Period(context.getString(R.string.period_biweekly_2),middleOfMonth,lastDayOfMonth),
                    Period(context.getString(R.string.period_monthly),firstDayOfMonth,lastDayOfMonth),
                    Period(context.getString(R.string.period_last_month),firstDayOfLastMonth,lastDayOfLastMonth),
                    Period(context.getString(R.string.period_select),isCustomPeriod = true)
            )
        }
    }
}
