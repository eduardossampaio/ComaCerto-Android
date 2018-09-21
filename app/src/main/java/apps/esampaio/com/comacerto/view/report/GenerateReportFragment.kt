package apps.esampaio.com.comacerto.view.report

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.service.report.ReportIteractor
import apps.esampaio.com.comacerto.core.service.report.ReportPresenter
import apps.esampaio.com.comacerto.core.service.report.ReportService
import apps.esampaio.com.comacerto.core.service.report.http.ReportHttpService
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.meals.register.AddNewMealActivity
import com.github.barteksc.pdfviewer.listener.OnErrorListener
import kotlinx.android.synthetic.main.fragment_generate_reports.*
import org.jetbrains.anko.runOnUiThread
import java.util.*


class GenerateReportFragment : BaseFragment(),ReportPresenter {

    lateinit var shareMenuItem : MenuItem
    var selectedPeriod: Period? = null
    var reportIteractor : ReportIteractor

    override fun displayGeneratedReport(report:ByteArray) {
        generate_report_message.visibility = View.GONE
        pdfView.visibility = View.VISIBLE
        pdfView.fromBytes(report)
                .enableSwipe(true)
                .spacing(0)
                .onError(OnErrorListener {
                    Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
                }).load()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GenerateReportFragment().apply {

        }
    }

    init{
        setHasOptionsMenu(true)
        reportIteractor= ReportService(this)
    }



    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.generate_report_menu,menu)
        shareMenuItem = menu?.findItem(R.id.share_report_menu_item)!!
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.generate_report_menu_item){
            generateReport()
            shareMenuItem.setEnabled(true)
        }else if(item?.itemId == R.id.share_report_menu_item){
            Toast.makeText(context,"Compartilhar relatório",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_generate_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        period_spinner.adapter = PeriodSpinnerAdapter(context!!)

        period_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                shareMenuItem.setEnabled(false)
                selectedPeriod = adapter?.getItemAtPosition(position) as Period
            }

        }

    }

    private fun generateReport(){
        if (selectedPeriod != null){
            val selectedPeriod = selectedPeriod as Period
            reportIteractor.onGenerateReportClicked(selectedPeriod.initialDate!!,selectedPeriod.finalDate!!)
        }
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            context?.runOnUiThread {
               try {
                   period_expandable_layout.expand(true)
               }catch (e: Exception){

               }
            }
        },200)
    }
}

