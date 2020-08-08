package apps.esampaio.com.comacerto.core.persistence;

import android.content.Context
import android.support.annotation.UiThread
import apps.esampaio.com.comacerto.core.entity.Food
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.beginOfDay
import apps.esampaio.com.comacerto.core.extensions.endOfDay
import apps.esampaio.com.comacerto.core.extensions.sameDay
import apps.esampaio.com.comacerto.core.persistence.converters.TimestampConverter
import apps.esampaio.com.comacerto.core.persistence.entities.FoodEntity
import apps.esampaio.com.comacerto.core.persistence.entities.MealAndFoods
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity
import apps.esampaio.com.comacerto.core.persistence.entities.WaterEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.util.*

class WaterPersistence(val context: Context) {

    fun saveWater(water: Water) {
        doAsync {
            val waterDAO = AppDatabase.getInstance(context)?.waterDAO()
            if(water != null){
                waterDAO?.save(WaterEntity(water.dateAndTime,water.quantity))
            }
        }
    }

    fun remove(water: Water) {
        doAsync {
            val waterDAO = AppDatabase.getInstance(context)?.waterDAO()
            if(water != null){
                waterDAO?.delete(WaterEntity(water.dateAndTime,water.quantity,water.id))
            }
        }
    }
    fun getWater(date: Date, result: (List<Water>) -> Unit) {
        getWaterList(date,date,result)
    }

    fun getWaterList(initialDate: Date, finalDate: Date,result: (List<Water>) -> Unit) {

        doAsync {
            var allWater = mutableListOf<Water>()
            val waterDao = AppDatabase.getInstance(context)?.waterDAO()
            val waterEntityList = waterDao?.listAll(initialDate.beginOfDay().time,finalDate.endOfDay().time)
            if (waterEntityList != null) {
                for (waterEntity in waterEntityList) {
                    allWater.add(waterEntity.toWater())
                }
                context.runOnUiThread {
                    result(allWater)
                }
            }else{
                context.runOnUiThread {
                    result(emptyList())
                }
            }
        }

    }

}
