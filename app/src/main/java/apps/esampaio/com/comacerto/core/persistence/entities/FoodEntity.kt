package apps.esampaio.com.comacerto.core.persistence.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import apps.esampaio.com.comacerto.core.entity.Food


@Entity(foreignKeys = [ForeignKey(entity = MealEntity::class,
        parentColumns = arrayOf("primaryKey"),
        childColumns = arrayOf("mealPrimaryKey"),
        onDelete = CASCADE)])
class FoodEntity {
    @PrimaryKey(autoGenerate = true)
    var primaryKey : Long? = 0
    var name:String = ""
    var category: String = ""
    var portion : Int = 1
    var mealPrimaryKey:Long? = 0
    constructor(){

    }
    constructor(food:Food, mealId: Long?){
        this.primaryKey = food.primaryKey
        this.name = food.name
        this.category = food.category
        this.portion = food.portion
        this.mealPrimaryKey = mealId
    }

    fun toFood() : Food{
        var food = Food(this.name,this.category)
        food.primaryKey = this.primaryKey
        food.portion = this.portion
        return food;
    }
}