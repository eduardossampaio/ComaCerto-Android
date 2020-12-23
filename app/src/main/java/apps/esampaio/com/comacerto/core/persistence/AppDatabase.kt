package apps.esampaio.com.comacerto.core.persistence

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import android.content.Context
import apps.esampaio.com.comacerto.core.persistence.dao.FoodDAO
import apps.esampaio.com.comacerto.core.persistence.dao.MealDAO
import apps.esampaio.com.comacerto.core.persistence.dao.MealTypeDAO
import apps.esampaio.com.comacerto.core.persistence.dao.WaterDAO
import apps.esampaio.com.comacerto.core.persistence.entities.FoodEntity
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity
import apps.esampaio.com.comacerto.core.persistence.entities.MealTypeEntity
import apps.esampaio.com.comacerto.core.persistence.entities.WaterEntity

//
@Database(entities = [MealEntity::class, FoodEntity::class, WaterEntity::class, MealTypeEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDAO

    abstract fun foodDao(): FoodDAO

    abstract fun waterDAO(): WaterDAO

    abstract fun mealTypeDAO(): MealTypeDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "weather.db")
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE 'WaterEntity' ('primaryKey' INTEGER PRIMARY KEY AUTOINCREMENT,  'quantity' INTEGER NOT NULL,'dateAndTime' INTEGER NOT NULL)")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `MealTypeEntity` (`primaryKey` INTEGER PRIMARY KEY AUTOINCREMENT,  `name` VARCHAR(256) NOT NULL,`iconName` VARCHAR(20) NOT NULL)")
                database.execSQL("ALTER TABLE 'MealEntity' ADD COLUMN customMeal  INTEGER");
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

    }
}
