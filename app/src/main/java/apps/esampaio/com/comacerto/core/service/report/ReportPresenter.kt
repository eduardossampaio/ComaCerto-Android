package apps.esampaio.com.comacerto.core.service.report

import apps.esampaio.com.comacerto.core.service.ViewPresenter

interface ReportPresenter : ViewPresenter {

    fun displayGeneratedReport(byte: ByteArray)
}