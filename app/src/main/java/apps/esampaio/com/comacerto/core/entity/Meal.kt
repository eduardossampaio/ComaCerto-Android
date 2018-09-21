package apps.esampaio.com.comacerto.core.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Meal : Serializable  {
    @Expose
    var primaryKey : Long? = null
    var mealType: MealType = MealType.Breakfast
    var whatDoing = ""
    @SerializedName("dateAndTime")
    var date = Date()
    var foods : List<Food> = emptyList()
    var hunger:Level = Level.hunger()
    var satiety:Level = Level.satiety()
    var feeling = Feeling.Natural
    constructor(){

    }
    constructor(primaryKey: Long?, mealType: MealType, whatDoing: String, date: Date, foods: List<Food>, hunger: Level, satiety: Level, feeling: Feeling) {
        this.primaryKey = primaryKey
        this.mealType = mealType
        this.whatDoing = whatDoing
        this.date = date
        this.foods = foods
        this.hunger = hunger
        this.satiety = satiety
        this.feeling = feeling
    }
}