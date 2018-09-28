package apps.esampaio.com.comacerto.core.service.food

import apps.esampaio.com.comacerto.core.entity.Food
import apps.esampaio.com.comacerto.core.service.ViewPresenter

interface FoodPresenter: ViewPresenter{
    fun updateDefaultFoodsList(list: List<Food>)
}
