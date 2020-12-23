package apps.esampaio.com.comacerto.core.service.meal.mealtype

import android.content.Context
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon
import apps.esampaio.com.comacerto.core.persistence.MealTypePersistence

class MealTypeService(context: Context,var  mealTypePresenter: MealTypePresenter? = null) : MealTypeIteractor {

    private val mealTypePersistence = MealTypePersistence(context);

    override fun onScreenLoaded() {
        loadAllAndPresent()
    }

    override fun newMealTypeAdded(name: String, mealTypeIcon: MealTypeIcon) {
       val mealType = MealType(name,mealTypeIcon )
        mealTypePersistence.saveMealType(mealType) {
            if(mealTypePresenter != null){
                mealTypePresenter?.newMealTypeAdded(mealType)
                loadAllAndPresent()
            }
        }
    }

    override fun deleteMealType(mealType: MealType) {
       mealTypePersistence.deleteMealType(mealType){
           loadAllAndPresent()
       }
    }

    private fun loadAllAndPresent(){
        if(mealTypePresenter != null){
            mealTypePersistence.getAll {
                mealTypePresenter?.updateCustomMealTypeList(it)
                var allMealTypes = MealType.defaultMealTypes;
                allMealTypes = allMealTypes.plus(it)
                mealTypePresenter?.updateAllMealTypeList(allMealTypes.toList())
            }

        }
    }

}