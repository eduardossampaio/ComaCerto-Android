package apps.esampaio.com.comacerto.core.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import apps.esampaio.com.comacerto.core.persistence.entities.MealAndFoods
import apps.esampaio.com.comacerto.core.persistence.entities.MealTypeEntity

@Dao
interface MealTypeDAO {

    @Insert
    fun save(entity: MealTypeEntity) : Long

    @Query("SELECT * from MealTypeEntity")
    fun getAll(): List<MealTypeEntity>

    @Query("SELECT * FROM MealTypeEntity m where m.primaryKey = :id")
    fun getById(id:Int) : MealTypeEntity

    @Delete
    fun delete(entity: MealTypeEntity)
}