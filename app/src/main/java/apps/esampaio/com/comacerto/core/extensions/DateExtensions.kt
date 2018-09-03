package apps.esampaio.com.comacerto.core.extensions

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