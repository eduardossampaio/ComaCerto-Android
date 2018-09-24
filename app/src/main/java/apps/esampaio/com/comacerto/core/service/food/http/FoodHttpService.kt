package apps.esampaio.com.comacerto.core.service.food.http

import apps.esampaio.com.comacerto.core.entity.Food
import apps.esampaio.com.comacerto.core.service.report.http.GenerateReportRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FoodHttpService  {


    @POST("/reports/meals")
    fun requestReport(): Call<Food>
}