package apps.esampaio.com.comacerto.core.persistence.dao

import androidx.room.*
import apps.esampaio.com.comacerto.core.persistence.entities.WaterEntity

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