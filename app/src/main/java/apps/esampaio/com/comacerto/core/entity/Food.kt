package apps.esampaio.com.comacerto.core.entity

import java.io.Serializable

data class Food(val name:String, val category: String, var portion : Int = 1) : Serializable  {
    var primaryKey : Long? = null


    override fun toString(): String {
        return name
    }


}