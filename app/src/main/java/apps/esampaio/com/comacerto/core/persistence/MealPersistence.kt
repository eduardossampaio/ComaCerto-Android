package apps.esampaio.com.comacerto.core.persistence;

import android.content.Context
import android.support.annotation.UiThread
import apps.esampaio.com.comacerto.core.entity.Food
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.beginOfDay
import apps.esampaio.com.comacerto.core.extensions.endOfDay
import apps.esampaio.com.comacerto.core.extensions.sameDay
import apps.esampaio.com.comacerto.core.persistence.converters.TimestampConverter
import apps.esampaio.com.comacerto.core.persistence.entities.FoodEntity
import apps.esampaio.com.comacerto.core.persistence.entities.MealAndFoods
import apps.esampaio.com.comacerto.core.persistence.entities.MealEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.util.*

class MealPersistence(val context: Context) {

    fun saveMeal(meal: Meal) {
        doAsync {
            val mealDao = AppDatabase.getInstance(context)?.mealDao()
            val foodDao = AppDatabase.getInstance(context)?.foodDao()
            if (mealDao != null) {
                val mealEntity = MealEntity(meal)
                val mealEntityId = mealDao.save(mealEntity)
                for (food in meal.foods) {
                    val foodEntity = FoodEntity(food, mealEntityId)
                    foodDao?.save(foodEntity)
                }
            }
        }
    }

    fun updateMeal(meal: Meal) {
        doAsync {
            val mealDao = AppDatabase.getInstance(context)?.mealDao()
            val foodDao = AppDatabase.getInstance(context)?.foodDao()
            if (mealDao != null) {
                val mealEntity = MealEntity(meal)
                //delete previous foods
                foodDao?.deleteFoodFromMeal(mealEntity.primaryKey!!)
                mealDao.update(mealEntity)
                for (food in meal.foods) {
                    val foodEntity = FoodEntity(food, meal.primaryKey)
                    foodDao?.save(foodEntity)
                }
            }
        }
    }

    fun getMeals(date: Date, result: (List<Meal>) -> Unit) {
        getMeals(date,date,result)
    }

    private fun foodsEntityToFood(entities: List<FoodEntity>?): List<Food> {
        if (entities == null) {
            return emptyList()
        }
        val foods = mutableListOf<Food>()
        for (entity in entities) {
            foods.add(entity.toFood())
        }
        return foods;
    }

    fun getMeals(initialDate: Date,finalDate:Date, result: (List<Meal>) -> Unit) {
        doAsync {
            var selectedMeals = mutableListOf<Meal>()
            val mealDao = AppDatabase.getInstance(context)?.mealDao()
            if (mealDao != null) {
                val mealAndFoodsList = mealDao.getAllInDateRange(initialDate.beginOfDay().time, finalDate.endOfDay().time)
                for (mealAndFoods in mealAndFoodsList) {
                    val mealEntity = mealAndFoods.meal
                    val foodsEntity = mealAndFoods.foods
                    val meal = mealEntity?.toMeal()
                    if (meal != null) {
                        meal.foods = foodsEntityToFood(foodsEntity)
                        selectedMeals.add(meal)
                    }
                }
                context.runOnUiThread {
                    result(selectedMeals)
                }
            }
        }
    }

    fun deleteMeal(meal: Meal) {
        doAsync {
            val mealDao = AppDatabase.getInstance(context)?.mealDao()
            if (mealDao != null) {
                val mealEntity = MealEntity(meal)
                mealDao.delete(mealEntity)
            }
        }
    }
}
