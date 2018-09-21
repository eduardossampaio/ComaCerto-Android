package apps.esampaio.com.comacerto.core.service.report

import android.content.Context
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.core.persistence.MealPersistence
import apps.esampaio.com.comacerto.core.service.http.RetrofitService
import apps.esampaio.com.comacerto.core.service.report.http.GenerateReportRequest
import apps.esampaio.com.comacerto.core.service.report.http.ReportHttpService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.util.*

class ReportService : ReportIteractor{

    val presenter:ReportPresenter

    constructor(presenter: ReportPresenter){
        this.context = MyApplication.instance
        this.presenter = presenter
        this.mealPersistence = MealPersistence(context)
        this.reportHttpService = RetrofitService().getReportHttpService()
    }

    override fun onGenerateReportClicked(initialDate: Date, finalDate: Date) {
        generateReport(initialDate,finalDate)
    }

    override fun onSharedReportClicked() {

    }

    val context:Context
    val mealPersistence:MealPersistence
    val reportHttpService:ReportHttpService

    private fun generateReport(initialDate: Date,finalDate: Date){
        mealPersistence.getMeals(initialDate,finalDate,{
            context.doAsync {
                val generatedReport = reportHttpService.requestReport(GenerateReportRequest(initialDate, finalDate, it)).execute()
                context.runOnUiThread {
                    if (generatedReport.body() != null) {
                        val bytes = generatedReport.body()!!.bytes()
                        presenter.displayGeneratedReport(bytes)
                    }
                }
            }
        })
    }

}