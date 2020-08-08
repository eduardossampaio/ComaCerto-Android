package apps.esampaio.com.comacerto.core.service.report.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ReportHttpService {

    @Headers(*arrayOf("Content-Type: application/json", "version:v3"))
    @POST("/reports/meals")
    fun requestReport(@Body request: GenerateReportRequest): Call<ResponseBody>

}