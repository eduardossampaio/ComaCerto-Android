package apps.esampaio.com.comacerto.core.persistence.DAO

import android.arch.persistence.room.*
import apps.esampaio.com.comacerto.core.persistence.entities.MealAndFoods
import apps.esampaio.com.comacerto.core.persistence.entities.WaterEntity
import java.util.*

@Dao
interface WaterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: WaterEntity) : Long

    @Query("SELECT * from WaterEntity")
    fun listAll(): List<WaterEntity>

    @Query("SELECT * from WaterEntity where dateAndTime BETWEEN :from AND :to order by dateAndTime asc")
    fun listAll(from: Long,to:Long): List<WaterEntity>

    @Delete
    fun delete(entity: WaterEntity) : Int

}