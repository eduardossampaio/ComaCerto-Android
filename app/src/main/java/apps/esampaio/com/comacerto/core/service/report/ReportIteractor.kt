package apps.esampaio.com.comacerto.core.service.report

import java.util.*

interface ReportIteractor {

    fun onGenerateReportClicked(initialDate: Date,finalDate: Date)

    fun onSharedReportClicked()
}