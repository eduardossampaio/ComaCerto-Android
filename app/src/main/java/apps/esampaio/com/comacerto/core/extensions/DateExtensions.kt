package apps.esampaio.com.comacerto.core.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

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

fun Date.getLastDayOfWeek() : Date {
    val calendar = Calendar.getInstance()
    calendar.time = getFirsDayOfWeek()
    calendar.add(Calendar.DAY_OF_WEEK,6)
    return calendar.time
}

fun Date.sameDay(other: Date): Boolean{
    val calendar1 = Calendar.getInstance()
    val calendar2 = Calendar.getInstance()
    calendar1.time = this
    calendar2.time = other
    return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}

fun Date.sameHour(other: Date): Boolean{
    val calendar1 = Calendar.getInstance()
    val calendar2 = Calendar.getInstance()
    calendar1.time = this
    calendar2.time = other
    return calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY) &&
            calendar1.get(Calendar.MINUTE) == calendar2.get(Calendar.MINUTE)
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

fun Date.addDay(): Date{
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH,1);
    return calendar.time
}

fun Date.subtractDay(): Date{
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH,-1);
    return calendar.time
}

fun Date.subtractDay(totalDays: Int): Date{
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH,-totalDays);
    return calendar.time
}

fun Date.differenceInDays(other:Date) : Int{
    val diffInMillis: Long = abs(other.time - this.time)
    val diff: Long = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)
    return diff.toInt();

//    return abs(other.dayOfYear() - this.dayOfYear());
}


fun Date.beginOfMonth(): Date{
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.DAY_OF_MONTH,1)
    return calendar.time
}

fun Date.endOfMonth(): Date{
    val calendar = Calendar.getInstance()
    val latDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    calendar.time = this
    calendar.set(Calendar.DAY_OF_MONTH,latDayOfMonth)
    return calendar.time
}

fun Date.getDateYear() :Int{
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.YEAR)
}
fun Date.asString(format: DateFormat): String = format.format(this)

fun Date.asString(format: String): String = asString(SimpleDateFormat(format))

fun Date.fromFormat(format: String,value:String): Date {
    val dateFormat = SimpleDateFormat(format)
    this.time = dateFormat.parse(value).time
    return this
}
fun Calendar.appendDate(date: Date,day:Int =-99 ,month:Int =-99 ,year:Int =-99 ) : Date{
    time = date
    if ( day!= -99) {
        set(Calendar.DAY_OF_MONTH, day)
    }
    if(month != -99) {
        set(Calendar.MONTH, month)
    }
    if(year != -99) {
        set(Calendar.YEAR, year)
    }
    return time
}
fun Calendar.lastMonth(date: Date) : Date{
    time = date
    add(Calendar.MONTH, -1)
    return time
}
fun Calendar.appendTime(date: Date,hour:Int,minute:Int,second:Int = 0) : Date{
    time = date
    set(Calendar.HOUR_OF_DAY,hour)
    set(Calendar.MINUTE,minute)
    set(Calendar.SECOND,second)
    return time
}