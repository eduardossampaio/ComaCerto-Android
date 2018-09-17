package apps.esampaio.com.comacerto.core.persistence.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import apps.esampaio.com.comacerto.core.entity.Feeling
import apps.esampaio.com.comacerto.core.entity.Level
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.persistence.converters.DateConverter
import apps.esampaio.com.comacerto.core.persistence.converters.TimestampConverter
import java.util.*

@Entity
class MealEntity {
    @PrimaryKey(autoGenerate = true)
    var primaryKey : Long? = null
    var mealType: Int = 0
    var whatDoing: String = ""
    @TypeConverters(DateConverter::class)
    var date = Date()
    var hunger: Int = 0
    var satiety:Int = 0
    var feeling: Int = 0

    constructor(){

    }
    constructor(meal: Meal){
        this.primaryKey = meal.primaryKey
        this.mealType = meal.mealType.ordinal
        this.whatDoing = meal.whatDoing
        this.date = meal.date
        this.hunger = meal.hunger.level
        this.satiety = meal.satiety.level
        this.feeling = meal.feeling.ordinal
    }

    fun toMeal() : Meal{
        var meal = Meal()
        meal.primaryKey = this.primaryKey
        meal.mealType = MealType.getByOrdinal(this.mealType)
        meal.whatDoing = this.whatDoing
        meal.date = this.date
        meal.hunger = Level.hunger(this.hunger)
        meal.satiety = Level.satiety(this.satiety)
        meal.feeling = Feeling.getByOrdinal(this.feeling)
        return meal;
    }

}