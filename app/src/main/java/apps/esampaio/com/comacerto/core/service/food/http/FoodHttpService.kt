package apps.esampaio.com.comacerto.core.service.food.http

import apps.esampaio.com.comacerto.core.entity.Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface FoodHttpService  {

    @GET()
    fun getAllFoods(@Url url:String): Call<List<Food>>
}