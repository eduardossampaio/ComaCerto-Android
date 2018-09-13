package apps.esampaio.com.comacerto.core.service.meal

import android.util.Log
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.persistence.MealPersistence
import apps.esampaio.com.comacerto.core.service.meal.MealIteractor
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import java.util.*
class MealService : MealIteractor {
    val mealPresenter: MealPresenter
    val mealPersistence = MealPersistence()

    constructor(mealPresenter:MealPresenter) {
        this.mealPresenter = mealPresenter
    }

    override fun dateSelected(date: Date) {
        Log.d("MealService","selected date ${date}")
        val mealsForDate = mealPersistence.getMeals(date)
//        val sortedMeals = mealsForDate.sorted(by: { $0.mealType.rawValue < $1.mealType.rawValue })
//        mealPresenter.updateMealList(meals: sortedMeals)
        mealPresenter.updateMealList(mealsForDate)
    }

    override fun onSavePressed(meal: Meal) {
        mealPersistence.saveOrUpdateMeal(meal)
        if (meal.primaryKey == null){
            mealPresenter.showAlert("Refeição adicionada com sucesso")
        }else{
            mealPresenter.showAlert("Refeição atualizada com sucesso")
        }
    }

    override fun onCancelPressed() {

    }

    override fun onDeletePressed(meal: Meal) {
        mealPersistence.deleteMeal(meal)
    }
}
