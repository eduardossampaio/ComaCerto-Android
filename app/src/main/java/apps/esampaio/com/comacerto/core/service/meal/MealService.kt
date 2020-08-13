package apps.esampaio.com.comacerto.core.service.meal

import android.util.Log
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.persistence.MealPersistence
import apps.esampaio.com.comacerto.core.persistence.WaterPersistence
import apps.esampaio.com.comacerto.core.service.meal.MealIteractor
import apps.esampaio.com.comacerto.core.service.meal.MealPresenter
import java.util.*
class MealService : MealIteractor {

    private val mealPresenter: MealPresenter
    private val mealPersistence = MealPersistence(MyApplication.instance)
    private val waterPersistence = WaterPersistence(MyApplication.instance)


    constructor(mealPresenter:MealPresenter) {
        this.mealPresenter = mealPresenter
    }

    override fun dateSelected(date: Date) {
        mealPersistence.getMeals(date) {
            val mealList = it
            waterPersistence.getWater(date) {
                val waterList = it
                mealPresenter.updateMealAndWaterList(date,mealList,waterList)
            }
//            mealPresenter.updateMealList(it)
        }
//        waterPersistence.getWater(date){
//            mealPresenter.updateWaterList(it)
//        }

    }

    override fun onUpdatePressed(meal: Meal) {
        mealPersistence.updateMeal(meal)
        mealPresenter.showAlert(MyApplication.instance.getString(R.string.meal_successful_edited_message)) {
            mealPresenter.finishScreen()
        }
    }

    override fun onSavePressed(meal: Meal) {
        mealPersistence.saveMeal(meal)
        mealPresenter.showAlert(MyApplication.instance.getString(R.string.meal_successful_added_message)) {
            mealPresenter.finishScreen()
        }
    }

    override fun onCancelPressed() {

    }

    override fun onDeletePressed(meal: Meal) {

        mealPresenter.showAskDialog(MyApplication.instance.getString(R.string.meal_delete_ask_message),onYesPressed = {
            mealPersistence.deleteMeal(meal)
            mealPresenter.finishScreen()
        });

    }
}
