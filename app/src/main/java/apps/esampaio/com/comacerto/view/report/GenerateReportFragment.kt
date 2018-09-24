package apps.esampaio.com.comacerto.view.report

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.ParcelFileDescriptor
import android.support.v4.content.FileProvider
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.service.report.ReportIteractor
import apps.esampaio.com.comacerto.core.service.report.ReportPresenter
import apps.esampaio.com.comacerto.core.service.report.ReportService
import apps.esampaio.com.comacerto.view.BaseFragment
import apps.esampaio.com.comacerto.view.custom.pdfviewpager.PDFViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_generate_reports.*
import org.jetbrains.anko.runOnUiThread
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.content.ContextCompat.startActivity
import android.content.ActivityNotFoundException
import android.support.v4.content.ContextCompat.startActivity
import android.webkit.MimeTypeMap
import apps.esampaio.com.comacerto.BuildConfig


class GenerateReportFragment : BaseFragment(), ReportPresenter {

    lateinit var shareMenuItem: MenuItem
    var selectedPeriod: Period? = null
    var reportIteractor: ReportIteractor
    lateinit var generatedReportFile: File
    override fun displayGeneratedReport(report: ByteArray) {
        generate_report_message.visibility = View.GONE

        generatedReportFile = createFile("report-pdf.pdf")
        writeToFile(report, generatedReportFile.absolutePath)
        pdfView.visibility = View.VISIBLE
        pdfView.adapter = PDFViewPagerAdapter(fragmentManager!!,generatedReportFile);
//        (pdfView.adapter as PDFViewPagerAdapter).notifyDataSetChanged()
    }


    @Throws(IOException::class)
    fun writeToFile(data: ByteArray, fileName: String) {
        val out = FileOutputStream(fileName)
        out.write(data)
        out.close()
    }

    fun createFile(fileName: String) : File{
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root)
        myDir.mkdirs()
        return File(myDir, fileName)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GenerateReportFragment().apply {

        }
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
            shareMenuItem.setEnabled(true)
        } else if (item?.itemId == R.id.share_report_menu_item) {
            shareGeneratedReport()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareGeneratedReport() {
        //showError("Essa funcionalidade está em desenvolvimento e estará dispoível em breve")
//        val uri = FileProvider.getUriForFile(context!!, context!!.getPackageName() + ".provider", generatedReportFile)
////        val  uri = Uri.fromFile(generatedReportFile)
//
//        val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = uri
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        val j = Intent.createChooser(intent, "Choose an application to open with:")
//        startActivity(j)
//        val sendIntent = Intent(Intent.ACTION_VIEW)
//        val uri = Uri.fromFile(generatedReportFile)
//        sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
//        sendIntent.setType("application/pdf")
//        startActivity(sendIntent)

//        ).setDataAndType(uri, "application/pdf")
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(intent)
        showFile(generatedReportFile,"pdf")
    }

    private fun showFile(file: File, filetype: String) {
        val myMime = MimeTypeMap.getSingleton()
        val intent = Intent(Intent.ACTION_VIEW)
        val mimeType = myMime.getMimeTypeFromExtension(filetype)
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            val fileURI = FileProvider.getUriForFile(context!!,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file)
            intent.setDataAndType(fileURI, mimeType)

        } else {
            intent.setDataAndType(Uri.fromFile(file), mimeType)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No Application found to open this type of file.", Toast.LENGTH_LONG).show()

        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_generate_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        period_spinner.adapter = PeriodSpinnerAdapter(context!!)

        period_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                shareMenuItem.setEnabled(false)
                selectedPeriod = adapter?.getItemAtPosition(position) as Period
            }

        }

    }

    private fun generateReport() {
        if (selectedPeriod != null) {
            val selectedPeriod = selectedPeriod as Period
            reportIteractor.onGenerateReportClicked(selectedPeriod.initialDate!!, selectedPeriod.finalDate!!)
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
}

