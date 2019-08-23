package apps.esampaio.com.comacerto.core.persistence.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.persistence.converters.DateConverter
import java.util.*

@Entity
class WaterEntity {
    @PrimaryKey(autoGenerate = true)
    var primaryKey : Long? = null

    @TypeConverters(DateConverter::class)
    var dateAndTime = Date()

    var quantity: Int = 0

    constructor(){

    }

    constructor(dateAndTime: Date, quantity: Int) {
        this.dateAndTime = dateAndTime
        this.quantity = quantity
    }

    constructor(dateAndTime: Date, quantity: Int, id:Long) {
        this.dateAndTime = dateAndTime
        this.quantity = quantity
        this.primaryKey = id;
    }
    fun toWater() : Water{
      return Water(this.dateAndTime,this.quantity,this.primaryKey!!)
    }


}