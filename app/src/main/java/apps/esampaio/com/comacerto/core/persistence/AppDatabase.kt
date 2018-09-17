package apps.esampaio.com.comacerto.core.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import apps.esampaio.com.comacerto.core.persistence.DAO.FoodDAO
import apps.esampaio.com.comacerto.core.persistence.DAO.MealDAO
import apps.esampaio.com.comacerto.core.persistence.entities.FoodEntity
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity

//
@Database(entities = arrayOf(MealEntity::class,FoodEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDAO

    abstract fun foodDao(): FoodDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "weather.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
