package apps.esampaio.com.comacerto.core.service.meal.mealtype

import apps.esampaio.com.comacerto.core.entity.MealTypeIcon

interface MealTypeIteractor {
    fun onScreenLoaded()

    fun newMealTypeAdded(name:String, mealTypeIcon: MealTypeIcon);
}