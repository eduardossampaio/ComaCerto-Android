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

}