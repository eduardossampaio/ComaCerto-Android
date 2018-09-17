package apps.esampaio.com.comacerto.core.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.getWeek() : Int{
    val calendar = Calendar.getInstance();
    calendar.time = this
    return calendar.get(Calendar.WEEK_OF_YEAR)
}

fun Date.getFirsDayOfWeek() : Date {
    val calendar = Calendar.getInstance()
    calendar.time = this

    while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.firstDayOfWeek) {
        calendar.add(Calendar.DATE, -1);
    }
    return calendar.time
}

fun Date.sameDay(other: Date): Boolean{
    val calendar1 = Calendar.getInstance()
    val calendar2 = Calendar.getInstance()
    calendar1.time = this
    calendar2.time = other
    return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}

fun Date.dayOfYear(): Int{
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_YEAR)
}

fun Date.beginOfDay() : Date{
    return Calendar.getInstance().appendTime(this,0,0,0);
}

fun Date.endOfDay() : Date{
    return Calendar.getInstance().appendTime(this,23,59,59);
}


fun Date.asString(format: DateFormat): String = format.format(this)

fun Date.asString(format: String): String = asString(SimpleDateFormat(format))

fun Calendar.appendDate(date: Date,day:Int,month:Int,year:Int) : Date{
    time = date
    set(Calendar.DAY_OF_MONTH,day)
    set(Calendar.MONTH,month)
    set(Calendar.YEAR,year)
    return time
}

fun Calendar.appendTime(date: Date,hour:Int,minute:Int,second:Int = 0) : Date{
    time = date
    set(Calendar.HOUR_OF_DAY,hour)
    set(Calendar.MINUTE,minute)
    set(Calendar.SECOND,second)
    return time
}