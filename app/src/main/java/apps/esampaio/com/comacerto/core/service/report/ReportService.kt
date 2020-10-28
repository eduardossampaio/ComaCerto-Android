package apps.esampaio.com.comacerto.core.service.report

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.print.PrintAttributes
import android.util.Log
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.core.extensions.fromFormat
import apps.esampaio.com.comacerto.core.persistence.MealPersistence
import apps.esampaio.com.comacerto.core.persistence.WaterPersistence
import apps.esampaio.com.comacerto.core.service.http.RetrofitService
import apps.esampaio.com.comacerto.core.service.report.http.GenerateReportRequest
import apps.esampaio.com.comacerto.core.service.report.http.ReportHttpService
import apps.esampaio.com.comacerto.core.utils.FileUtils

import com.uttampanchasara.pdfgenerator.CreatePdf
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import java.io.File
import java.util.*




class ReportService : ReportIteractor {

    private val presenter: ReportPresenter

    val context: Context
    val mealPersistence: MealPersistence
    val waterPersistence: WaterPersistence
    val reportHttpService: ReportHttpService

    constructor(presenter: ReportPresenter) {
        this.context = MyApplication.instance
        this.presenter = presenter
        this.mealPersistence = MealPersistence(context)
        this.waterPersistence = WaterPersistence(context)
        this.reportHttpService = RetrofitService().getReportHttpService()
    }

    override fun onGenerateReportClicked(initialDate: Date, finalDate: Date) {
        generateReport(initialDate, finalDate)
    }

    override fun onSharedReportClicked() {

    }


    private fun generateReport(initialDate: Date, finalDate: Date) {
        mealPersistence.getMeals(initialDate, finalDate) { meals ->
            if (meals.isEmpty()) {
                presenter.showError(context.getString(R.string.error_no_meal_in_period))
            } else {
                waterPersistence.getWaterList(initialDate, finalDate) { waters ->
//                    requestReportFromServer(initialDate,finalDate,meals,waters)
                    generateReportFromHtml(initialDate, finalDate, meals, waters)
                }
            }
        }
    }

    private fun generateHtml(initialDate: Date, finalDate: Date, meals: List<Meal>, waters: List<Water>): String {

        val resultHtml = HtmlReportGenerator(context).generateHtml(initialDate,finalDate,meals,waters)
        Log.d("Html", resultHtml);
        return resultHtml;
    }

    private fun generateReportFromHtml(initialDate: Date, finalDate: Date, meals: List<Meal>, waters: List<Water>) {
        //Toast.makeText(MyApplication.instance, "Gerando do html", Toast.LENGTH_SHORT).show();
        presenter.showLoading(context.getString(R.string.generating_meals_report))

        val html = generateHtml(initialDate,finalDate,meals,waters);
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        FileUtils.deleteFile(root,"report.pdf");
        CreatePdf(MyApplication.instance)
                .setPdfName("report")
                .openPrintDialog(false)
                .setContentBaseUrl(null)
                .setPageSize(PrintAttributes.MediaSize.ISO_A4)
                .setContent(html)
                .setFilePath(root)
                .setCallbackListener(object : CreatePdf.PdfCallbackListener {
                    override fun onFailure(errorMsg: String) {
                        presenter.hideLoading()
                        presenter.showError(errorMsg)
                    }

                    override fun onSuccess(filePath: String) {
                        presenter.hideLoading()
                        val file = File(filePath);

                        Handler().postDelayed({
                            presenter.displayGeneratedReport(file);
                        },1000);
                    }
                })
                .create()
    }

    private fun requestReportFromServer(initialDate: Date, finalDate: Date, meals: List<Meal>, waters: List<Water>) {
        presenter.showLoading(context.getString(R.string.generating_meals_report))
        context.doAsync {
            val generatedReport = reportHttpService.requestReport(GenerateReportRequest(initialDate, finalDate, meals, waters)).execute()
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