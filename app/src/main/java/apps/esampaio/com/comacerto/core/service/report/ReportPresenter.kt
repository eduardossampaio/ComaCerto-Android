package apps.esampaio.com.comacerto.core.service.report

import apps.esampaio.com.comacerto.core.service.ViewPresenter
import java.io.File

interface ReportPresenter : ViewPresenter {

    fun displayGeneratedReport(byte: ByteArray)

    fun displayGeneratedReport(file: File)
}