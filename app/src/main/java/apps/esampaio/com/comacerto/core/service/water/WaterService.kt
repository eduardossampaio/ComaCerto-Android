package apps.esampaio.com.comacerto.core.service.water

import android.content.Context
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.persistence.WaterPersistence

class WaterService : WaterIteractor {


    var context: Context;

    constructor(context: Context) {
        this.context = context
    }


    override fun onWaterSavedClick(water: Water) {
        WaterPersistence(context).saveWater(water)
    }

    override fun onWaterRemoved(water: Water) {
        WaterPersistence(context).remove(water)
    }
}