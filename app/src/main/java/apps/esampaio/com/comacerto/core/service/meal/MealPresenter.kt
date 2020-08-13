package apps.esampaio.com.comacerto.core.service.meal

import apps.esampaio.com.comacerto.core.service.ViewPresenter

import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import java.util.*

interface MealPresenter : ViewPresenter {

    fun updateMealAndWaterList(date: Date, meals: List<Meal>, water: List<Water>) {

    }

}
