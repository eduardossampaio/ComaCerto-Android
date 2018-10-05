package apps.esampaio.com.comacerto.core.service.food

import android.content.Context
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.core.entity.Food
import apps.esampaio.com.comacerto.core.firebase.RemoteConfig
import apps.esampaio.com.comacerto.core.service.CacheService
import apps.esampaio.com.comacerto.core.service.food.http.FoodHttpService
import apps.esampaio.com.comacerto.core.service.http.RetrofitService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import st.lowlevel.storo.Storo
import st.lowlevel.storo.Get
import com.google.gson.reflect.TypeToken




class FoodService(val context: Context, val foodPresenter: FoodPresenter) : FoodInteractor {
    val foodHttpService = RetrofitService().getFoodHttpService()
    val cacheService = CacheService()
    val cacheListKey = "DEFAULT_FOODS_LIST_${MyApplication.instance.getCurrentLocale().toString()}"

    override fun screenLoaded() {

        val type = object : TypeToken<ArrayList<Food>>() {}.type

        val cachedList = Storo.get<List<Food>?>(cacheListKey,object : TypeToken<ArrayList<Food>>() {}.type).execute()
        if(cachedList == null || cachedList.isEmpty()) {
            fetchFoodsList()
        }else{
            //val foodsList = Storo.get(cacheListKey, List::class.java).execute() as List<Food>
            foodPresenter.updateDefaultFoodsList(cachedList)
        }
    }


    fun fetchFoodsList(){
        context.doAsync {
            try {
                val firebaseRemoteConfig = RemoteConfig.getInstance()
                var listJsonUrl = firebaseRemoteConfig.foodsListJson
                val allFoods = foodHttpService.getAllFoods(listJsonUrl).execute()
                if (allFoods.isSuccessful && allFoods.body() != null) {
                    context.runOnUiThread {
                        foodPresenter.updateDefaultFoodsList(allFoods.body()!!)
                        Storo.put(cacheListKey, allFoods.body()!!).execute()
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}