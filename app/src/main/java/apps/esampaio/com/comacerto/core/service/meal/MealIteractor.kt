package apps.esampaio.com.comacerto.core.service.meal


import java.util.Date

import apps.esampaio.com.comacerto.core.entity.Meal

interface MealIteractor {
    fun dateSelected(date: Date)

    fun onSavePressed(meal: Meal)

    fun onUpdatePressed(meal: Meal)

    fun onDeletePressed(meal: Meal)

    fun onCancelPressed()
}
