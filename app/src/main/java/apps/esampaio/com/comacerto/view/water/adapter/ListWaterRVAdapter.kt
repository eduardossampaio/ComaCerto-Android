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

class ListWaterRVAdapter(val context: Context,val waterList:MutableList<Water>) : RecyclerView.Adapter<ListWaterRVAdapter.ListWaterRVAdapterAdapter>() {
    class ListWaterRVAdapterAdapter(itemView: View) : RecyclerView.ViewHolder(itemView){
            val time:TextView = itemView.findViewById(R.id.water_hour)
            var date: TextView = itemView.findViewById(R.id.water_day)
            val quantity:TextView = itemView.findViewById(R.id.water_quantity)
        //setHasStableIds(true)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListWaterRVAdapterAdapter {
        return ListWaterRVAdapterAdapter(LayoutInflater.from(context).inflate(R.layout.list_water_item,viewGroup,false))
    }

    override fun getItemCount(): Int {
        return waterList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    fun removeAt(position:Int): Water{
        val removedItem = waterList.get(position);
        waterList.removeAt(position)
        notifyItemRemoved(position)
        return removedItem;
    }

    override fun onBindViewHolder(listWaterRVAdapterAdapter: ListWaterRVAdapterAdapter, i: Int) {
        val water = waterList.get(i);
        listWaterRVAdapterAdapter.date.text = water.dateAndTime.asString("dd/MM/yyyy")
        listWaterRVAdapterAdapter.time.text = water.dateAndTime.asString("HH:mm")
        listWaterRVAdapterAdapter.quantity.text = "${context.resources.getQuantityString(R.plurals.water_cups_consumed,water.quantity,water.quantity)}"
    }
}
