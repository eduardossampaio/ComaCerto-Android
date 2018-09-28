package apps.esampaio.com.comacerto.core.service.http

import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.core.entity.Feeling
import apps.esampaio.com.comacerto.core.entity.Level
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.service.food.http.FoodHttpService
import apps.esampaio.com.comacerto.core.service.http.converters.FeelingAdapter
import apps.esampaio.com.comacerto.core.service.http.converters.LevelAdapter
import apps.esampaio.com.comacerto.core.service.http.converters.MealTypeAdapter
import apps.esampaio.com.comacerto.core.service.report.http.ReportHttpService
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


class RetrofitService {
    val retrofit: Retrofit
    val baseURL = "https://coma-certo.herokuapp.com/"

    constructor() {

        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        val gson = GsonBuilder()
                .setDateFormat("dd/MM/yyyy HH:mm")
                .registerTypeAdapter(Level::class.java,LevelAdapter())
                .registerTypeAdapter(Feeling::class.java,FeelingAdapter())
                .registerTypeAdapter(MealType::class.java,MealTypeAdapter())
                .create()

        this.retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    fun getReportHttpService(): ReportHttpService {
        return retrofit.create(ReportHttpService::class.java)
    }

    fun getFoodHttpService(): FoodHttpService{
        return retrofit.create(FoodHttpService::class.java)
    }
}