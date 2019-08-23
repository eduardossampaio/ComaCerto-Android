package apps.esampaio.com.comacerto.core.service.water

import apps.esampaio.com.comacerto.core.entity.Water

interface WaterIteractor {
    fun onWaterSavedClick(water: Water)

    fun onWaterRemoved(water: Water)
}