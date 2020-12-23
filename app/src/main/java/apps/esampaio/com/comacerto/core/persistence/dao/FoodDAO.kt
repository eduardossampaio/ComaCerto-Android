package apps.esampaio.com.comacerto.core.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import apps.esampaio.com.comacerto.core.persistence.entities.FoodEntity

@Dao
interface FoodDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: FoodEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(entity: List<FoodEntity>) :Array<Long>

    @Query("delete from FoodEntity where mealPrimaryKey = :mealPrimaryKey")
    fun deleteFoodFromMeal(mealPrimaryKey: Long)
}