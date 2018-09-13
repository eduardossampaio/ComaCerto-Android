package apps.esampaio.com.comacerto.core.service.meal

import apps.esampaio.com.comacerto.core.service.ViewPresenter

import apps.esampaio.com.comacerto.core.entity.Meal

interface MealPresenter : ViewPresenter {
    fun updateMealList(meals: List<Meal>)

}
