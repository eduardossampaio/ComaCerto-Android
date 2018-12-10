package apps.esampaio.com.comacerto.view.water.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Water
import apps.esampaio.com.comacerto.core.extensions.asString
import java.text.DateFormat

class ListWaterRVAdapter(val context: Context,val waterList:List<Water>) : RecyclerView.Adapter<ListWaterRVAdapter.ListWaterRVAdapterAdapter>() {
    class ListWaterRVAdapterAdapter(itemView: View) : RecyclerView.ViewHolder(itemView){
            val time:TextView = itemView.findViewById(android.R.id.text1)
            val quantity:TextView = itemView.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListWaterRVAdapterAdapter {
        return ListWaterRVAdapterAdapter(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2,viewGroup,false))
    }

    override fun getItemCount(): Int {
        return waterList.size
    }

    override fun onBindViewHolder(listWaterRVAdapterAdapter: ListWaterRVAdapterAdapter, i: Int) {
        val water = waterList.get(i);
        listWaterRVAdapterAdapter.time.text = water.dateAndTime.asString("dd/MM/yyyy HH:mm")
        listWaterRVAdapterAdapter.quantity.text = "${context.resources.getQuantityString(R.plurals.water_cups_consumed,water.quantity,water.quantity)}"
    }
}
