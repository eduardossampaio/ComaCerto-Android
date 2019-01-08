package apps.esampaio.com.comacerto.core.service.report

import android.content.Context
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.persistence.MealPersistence
import apps.esampaio.com.comacerto.core.service.http.RetrofitService
import apps.esampaio.com.comacerto.core.service.report.http.GenerateReportRequest
import apps.esampaio.com.comacerto.core.service.report.http.ReportHttpService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.util.*

class ReportService : ReportIteractor{

    private val presenter:ReportPresenter

    val context:Context
    val mealPersistence:MealPersistence
    val reportHttpService:ReportHttpService

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


    private fun generateReport(initialDate: Date,finalDate: Date){
        mealPersistence.getMeals(initialDate,finalDate) { meals ->
            if ( meals.isEmpty()){
                presenter.showError(context.getString(R.string.error_no_meal_in_period))
            }else{
                requestReportFromServer(initialDate,finalDate,meals)
            }
        }
    }

    private fun requestReportFromServer(initialDate: Date,finalDate: Date,meals:List<Meal>){
        presenter.showLoading(context.getString(R.string.generating_meals_report))
        context.doAsync {
            val generatedReport = reportHttpService.requestReport(GenerateReportRequest(initialDate, finalDate, meals)).execute()
            context.runOnUiThread {
                presenter.hideLoading()
                if (generatedReport.body() != null) {
                    val bytes = generatedReport.body()!!.bytes()
                    presenter.displayGeneratedReport(bytes)
                }
            }
        }
    }
}