package apps.esampaio.com.comacerto.view.report


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler

import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.AdapterView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.service.report.ReportIteractor
import apps.esampaio.com.comacerto.core.service.report.ReportPresenter
import apps.esampaio.com.comacerto.core.service.report.ReportService
import apps.esampaio.com.comacerto.core.utils.FileUtils
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.custom.pdfviewpager.PDFViewPagerAdapter
import apps.esampaio.com.comacerto.view.dialogs.DateRangePickerDialog
import kotlinx.android.synthetic.main.fragment_generate_reports.*
import org.jetbrains.anko.runOnUiThread
import java.io.File
import java.util.*


class GenerateReportFragment : BaseFragment(), ReportPresenter {

    companion object {
        const val REQUEST_PERMISSIONS_FOR_GENERATE_REPORT =  123
        @JvmStatic
        fun newInstance() = GenerateReportFragment().apply {

        }
    }

    lateinit var shareMenuItem: MenuItem
    var selectedPeriod: Period? = null
    var reportIteractor: ReportIteractor
    lateinit var periodSpinnerAdapter : PeriodSpinnerAdapter;

    lateinit var generatedReportFile: File

    override fun displayGeneratedReport(report: ByteArray) {
        generate_report_message.visibility = View.GONE
        generatedReportFile = FileUtils.saveDataToFile("report.pdf",report)
        pdfView.visibility = View.VISIBLE
        pdfView.fromBytes(report)
                .defaultPage(0)
                .swipeHorizontal(true)
                .load()
    }

    override fun displayGeneratedReport(file: File) {
        generate_report_message.visibility = View.GONE
        pdfView.visibility = View.VISIBLE
        generatedReportFile = file;
        pdfView.fromFile(file)
                .defaultPage(0)
                .swipeHorizontal(true)
                .load()
    }

    init {
        setHasOptionsMenu(true)
        reportIteractor = ReportService(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.generate_report_menu, menu)
        shareMenuItem = menu?.findItem(R.id.share_report_menu_item)!!
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.generate_report_menu_item) {
            generateReport()
            shareMenuItem.isEnabled = true
        } else if (item?.itemId == R.id.share_report_menu_item) {
            shareGeneratedReport()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareGeneratedReport() {
        FileUtils.shareFile(context!!,generatedReportFile,"pdf")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_generate_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        periodSpinnerAdapter = PeriodSpinnerAdapter(context!!)
        period_spinner.adapter = periodSpinnerAdapter

        period_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                shareMenuItem.isEnabled = false
                selectedPeriod = adapter?.getItemAtPosition(position) as Period

                if (selectedPeriod?.isCustomPeriod!!){
                    val dateRangePickerDialog = DateRangePickerDialog(context!!)
                    dateRangePickerDialog.onDateRangeSelectedListener = object : DateRangePickerDialog.OnDateRangeSelectedListener{
                        override fun onDateRangeSelected(initialDate: Date, finalDate: Date) {
                            periodSpinnerAdapter.updateCustomPeriodDate(initialDate,finalDate)
                        }

                    }
                    dateRangePickerDialog.show()
                }
            }

        }
        //loadPreviousGeneratedReport();
    }

    private fun generateReport() {
        if (selectedPeriod != null) {

            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                val selectedPeriod = selectedPeriod as Period
                reportIteractor.onGenerateReportClicked(selectedPeriod.initialDate!!, selectedPeriod.finalDate!!)
            }else{
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_PERMISSIONS_FOR_GENERATE_REPORT)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ( requestCode == REQUEST_PERMISSIONS_FOR_GENERATE_REPORT && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            generateReport()
        }
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            context?.runOnUiThread {
                try {
                    period_expandable_layout.expand(true)

                } catch (e: Exception) {

                }
            }
        }, 200)

    }

    private fun loadPreviousGeneratedReport() {
        val file = FileUtils.createFile("report.pdf");
        if(file.exists()){
            generate_report_message.visibility = View.GONE

            pdfView.visibility = View.VISIBLE
            pdfView.fromFile(file)
                    .defaultPage(0)
                    .swipeHorizontal(true)
                    .load()
        }
    }
}

