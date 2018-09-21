package apps.esampaio.com.comacerto.core.service.report.http

import apps.esampaio.com.comacerto.core.entity.Meal
import java.util.*

data class GenerateReportRequest(val initialDate:Date,val finalDate:Date,val meals:List<Meal>) {
}