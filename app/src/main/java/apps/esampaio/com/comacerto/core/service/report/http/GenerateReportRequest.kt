package apps.esampaio.com.comacerto.core.service.report.http

import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.Water
import java.util.*

data class GenerateReportRequest(val initialDate:Date,val finalDate:Date,val meals:List<Meal>,val waters:List<Water>) {
}