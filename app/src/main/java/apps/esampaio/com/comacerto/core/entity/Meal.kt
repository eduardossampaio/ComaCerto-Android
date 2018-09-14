package apps.esampaio.com.comacerto.core.entity

import java.io.Serializable
import java.util.*

class Meal : Serializable  {
    var primaryKey  = ""
    var mealType: MealType = MealType.None
    var whatDoing = ""
    var date = Date()
    var foods : List<Food> = emptyList()
    var hunger:Level = Level.hunger()
    var satiety:Level = Level.satiety()
    var feeling = Feeling.None
    constructor(){

    }
    constructor(primaryKey: String, mealType: MealType, whatDoing: String, date: Date, foods: List<Food>, hunger: Level, satiety: Level, feeling: Feeling) {
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