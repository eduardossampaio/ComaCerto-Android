package apps.esampaio.com.comacerto.core.persistence.entities


import androidx.room.Embedded
import androidx.room.Relation
import apps.esampaio.com.comacerto.core.entity.Meal


class MealAndFoods {

    @Embedded
    var meal: MealEntity? = null
    @Relation(parentColumn = "primaryKey", entityColumn = "mealPrimaryKey", entity = FoodEntity::class)
    var foods: List<FoodEntity>? = null

}