package apps.esampaio.com.comacerto.core.service.http

import apps.esampaio.com.comacerto.core.entity.Level
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
                .registerTypeAdapter(Level::class.java,object : JsonSerializer<Level>{
                    override fun serialize(src: Level?, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
                        var levelValue = ""
                        if ( src != null){
                            val level = src as Level
                            levelValue = level.levelNames[level.level]
                        }
                        return  context.serialize(levelValue, String::class.java)
                    }


                })
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
}