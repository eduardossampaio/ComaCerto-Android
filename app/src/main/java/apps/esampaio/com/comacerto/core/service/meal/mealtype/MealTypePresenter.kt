package apps.esampaio.com.comacerto.core.service.meal.mealtype

import apps.esampaio.com.comacerto.core.entity.MealType

interface MealTypePresenter {
    fun updateCustomMealTypeList(mealTypeList: List<MealType>){

    }

    fun updateAllMealTypeList(mealTypeList: List<MealType>){

    }

    fun newMealTypeAdded(mealType: MealType){

    }
}