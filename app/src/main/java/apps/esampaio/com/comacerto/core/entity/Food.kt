package apps.esampaio.com.comacerto.core.entity

import java.io.Serializable

data class Food(val name:String, val category: String) : Serializable  {
    var primaryKey : Long? = null
    var portion : Int = 1

    override fun toString(): String {
        return name
    }


}