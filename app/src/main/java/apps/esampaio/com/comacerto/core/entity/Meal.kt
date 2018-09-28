package apps.esampaio.com.comacerto.core.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Meal : Serializable  {
    @Expose
    var primaryKey : Long? = null
    var mealType: MealType = MealType.Breakfast
    @SerializedName("whatWasDoing")
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Meal

        if (mealType != other.mealType) return false
        if (whatDoing != other.whatDoing) return false
        if (date != other.date) return false
        if (foods != other.foods) return false
        if (hunger != other.hunger) return false
        if (satiety != other.satiety) return false
        if (feeling != other.feeling) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mealType.hashCode()
        result = 31 * result + whatDoing.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + foods.hashCode()
        result = 31 * result + hunger.hashCode()
        result = 31 * result + satiety.hashCode()
        result = 31 * result + feeling.hashCode()
        return result
    }


}