package apps.esampaio.com.comacerto.core.persistence.DAO

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.persistence.entities.MealAndFoods
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity
import java.util.*

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
