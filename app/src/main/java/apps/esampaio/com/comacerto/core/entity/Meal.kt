package apps.esampaio.com.comacerto.core.entity

import java.util.*

class Meal  {
    var primaryKey  = ""
    var mealType: MealType = MealType.None
    var whatDoing = ""
    var date = Date()
    var foods : Array<Food> = emptyArray()
    var hunger:Level = Level.hunger()
    var satiety:Level = Level.satiety()
    var feeling = Feeling.None
}