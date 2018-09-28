package apps.esampaio.com.comacerto.core.service.food

import android.content.Context
import apps.esampaio.com.comacerto.core.firebase.RemoteConfig
import apps.esampaio.com.comacerto.core.service.food.http.FoodHttpService
import apps.esampaio.com.comacerto.core.service.http.RetrofitService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread


class FoodService(val context: Context, val foodPresenter: FoodPresenter) : FoodInteractor {
    val foodHttpService = RetrofitService().getFoodHttpService()

    override fun screenLoaded() {
        fetchFoodsList()
    }



    fun fetchFoodsList(){
        context.doAsync {
            val firebaseRemoteConfig = RemoteConfig.getInstance()
            val listJsonUrl = firebaseRemoteConfig.foodsListJson
            val allFoods = foodHttpService.getAllFoods(listJsonUrl).execute()
            if (allFoods.isSuccessful && allFoods.body() != null) {
                context.runOnUiThread {
                    foodPresenter.updateDefaultFoodsList(allFoods.body()!!)
                }
            }
        }
    }
}