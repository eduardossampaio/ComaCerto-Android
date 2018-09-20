package apps.esampaio.com.comacerto.view.extensions

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment
import java.util.*

fun RadialTimePickerDialogFragment.setStartTime(date:Date){
    val calendar = Calendar.getInstance()
    calendar.time = date
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    setStartTime(hour,minute)
}