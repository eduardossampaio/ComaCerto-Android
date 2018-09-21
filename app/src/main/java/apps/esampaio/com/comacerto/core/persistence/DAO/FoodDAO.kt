package apps.esampaio.com.comacerto.core.persistence.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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