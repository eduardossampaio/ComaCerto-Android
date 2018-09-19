package apps.esampaio.com.comacerto.view.report


import apps.esampaio.com.comacerto.core.extensions.asString
import java.util.Date

class Period {
    var displayText: String? = null
    var initialDate: Date? = null
    var finalDate: Date? = null

    constructor(){

    }
    constructor(displayText:String){
        this.displayText = displayText
    }
    constructor(displayText:String,initialDate: Date,finalDate: Date){
        this.displayText = displayText
        this.initialDate = initialDate
        this.finalDate = finalDate
    }

    fun getPeriodRangeDisplayText() : String {
        if (initialDate == null || finalDate == null){
            return ""
        }
        return "${initialDate?.asString("dd/MM/yyyy")} - ${finalDate?.asString("dd/MM/yyyy")}"
    }
}
