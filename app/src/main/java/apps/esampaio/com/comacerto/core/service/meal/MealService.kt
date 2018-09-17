package apps.esampaio.com.comacerto.core.service.meal

import android.util.Log
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.persistence.MealPersistence
import apps.esampaio.com.comacerto.core.service.meal.MealIteractor
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import java.util.*
class MealService : MealIteractor {

    val mealPresenter: MealPresenter
    val mealPersistence = MealPersistence(MyApplication.instance)

    constructor(mealPresenter:MealPresenter) {
        this.mealPresenter = mealPresenter
    }

    override fun dateSelected(date: Date) {
        mealPersistence.getMeals(date, {
            mealPresenter.updateMealList(it)
        })

    }

    override fun onUpdatePressed(meal: Meal) {
        mealPersistence.updateMeal(meal)
        mealPresenter.showAlert("Refeição atualizada com sucesso")
        mealPresenter.finishScreen()
    }

    override fun onSavePressed(meal: Meal) {
        mealPersistence.saveMeal(meal)
        mealPresenter.showAlert("Refeição adicionada com sucesso")
        mealPresenter.finishScreen()
    }

    override fun onCancelPressed() {

    }

    override fun onDeletePressed(meal: Meal) {
        mealPersistence.deleteMeal(meal)
        mealPresenter.showAlert("Refeição removida com sucesso")
        mealPresenter.finishScreen()
    }
}
