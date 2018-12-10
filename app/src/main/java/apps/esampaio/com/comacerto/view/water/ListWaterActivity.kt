package apps.esampaio.com.comacerto.view.water

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.view.water.adapter.ListWaterRVAdapter
import kotlinx.android.synthetic.main.activity_list_water.*

class ListWaterActivity : AppCompatActivity() {

    companion object {
        val PARAM_WATER_LIST = "PARAM_WATER_LIST"
        fun createIntent(context: Context,waterList: ArrayList<Water>) : Intent{
            val intent = Intent(context,ListWaterActivity::class.java)
            intent.putParcelableArrayListExtra(PARAM_WATER_LIST,waterList)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_water)
        if(intent!=null) {
            val waterList = intent.extras.getSerializable(PARAM_WATER_LIST) as List<Water>
            list_water_rv.adapter = ListWaterRVAdapter(this, waterList)
            list_water_rv.layoutManager = LinearLayoutManager(this)
        }
        setTitle(R.string.consumed_water)
    }
}
