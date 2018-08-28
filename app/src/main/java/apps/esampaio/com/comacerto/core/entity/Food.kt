package apps.esampaio.com.comacerto.core.entity

data class Food(val name:String, val category: String)  {
    var primaryKey : String? = null
    var portion : Int? = 1
}