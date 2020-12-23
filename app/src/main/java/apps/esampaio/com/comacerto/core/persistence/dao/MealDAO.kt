package apps.esampaio.com.comacerto.core.persistence.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import apps.esampaio.com.comacerto.core.persistence.entities.MealAndFoods
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity

@Dao
interface MealDAO{
    @Query("SELECT * from MealEntity where date  BETWEEN :from AND :to ORDER BY mealType asc")
    fun getAllInDateRange(from: Long,to: Long): List<MealAndFoods>

    @Insert
    fun save(entity: MealEntity) : Long

    @Update(onConflict = REPLACE)
    fun update(entity: MealEntity)

    @Delete
    fun delete(entity: MealEntity)
}
