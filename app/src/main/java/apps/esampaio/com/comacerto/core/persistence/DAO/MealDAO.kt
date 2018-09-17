package apps.esampaio.com.comacerto.core.persistence.DAO

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.persistence.entities.MealAndFoods
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity

@Dao
interface MealDAO{
    @Query("SELECT * from MealEntity")
    fun getAll(): List<MealAndFoods>

    @Insert
    fun save(entity: MealEntity) : Long

    @Update
    fun update(entity: MealEntity)

    @Delete
    fun delete(entity: MealEntity)
}
