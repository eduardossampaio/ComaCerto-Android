package apps.esampaio.com.comacerto.core.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import apps.esampaio.com.comacerto.core.entity.Feeling
import apps.esampaio.com.comacerto.core.entity.Level
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.persistence.converters.DateConverter
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
    var customMeal : Int =0;

    constructor(){

    }
    constructor(meal: Meal){
        this.primaryKey = meal.primaryKey
        this.whatDoing = meal.whatDoing
        this.date = meal.date
        this.hunger = meal.hunger.level
        this.satiety = meal.satiety.level
        this.feeling = meal.feeling.ordinal

        if(MealType.isDefaultMealType(meal.mealType)){
            this.mealType = meal.mealType.mealId;
            this.customMeal = -1
        }else{
            this.customMeal = meal.mealType.mealId;
            this.mealType = -1
        }

    }

    fun toMeal() : Meal{
        var meal = Meal()
        meal.primaryKey = this.primaryKey
        if(MealType.isDefaultMealType(meal.mealType)) {
            meal.mealType = MealType.getDefaultMealType(this.mealType!!)
        }
        meal.whatDoing = this.whatDoing
        meal.date = this.date
        meal.hunger = Level.hunger(this.hunger)
        meal.satiety = Level.satiety(this.satiety)
        meal.feeling = Feeling.getByOrdinal(this.feeling)
        return meal;
    }

}