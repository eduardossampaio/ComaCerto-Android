package apps.esampaio.com.comacerto.core.persistence

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import apps.esampaio.com.comacerto.core.persistence.DAO.FoodDAO
import apps.esampaio.com.comacerto.core.persistence.DAO.MealDAO
import apps.esampaio.com.comacerto.core.persistence.DAO.WaterDAO
import apps.esampaio.com.comacerto.core.persistence.entities.FoodEntity
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity
import apps.esampaio.com.comacerto.core.persistence.entities.WaterEntity

//
@Database(entities = arrayOf(MealEntity::class,FoodEntity::class,WaterEntity::class), version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDAO

    abstract fun foodDao(): FoodDAO

    abstract fun waterDAO(): WaterDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "weather.db")
                            .addMigrations(MIGRATION_1_2)
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE 'WaterEntity' ('primaryKey' INTEGER PRIMARY KEY AUTOINCREMENT,  'quantity' INTEGER NOT NULL,'dateAndTime' INTEGER NOT NULL)")
            }
        }

    }
}
