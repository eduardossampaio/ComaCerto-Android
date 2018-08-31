package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context
import apps.esampaio.com.comacerto.core.entity.Feeling

class FeelingsListRecyclerViewAdapter(context: Context) : ImageRecyclerViewAdapter(context) {

    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i)
        val feeling = Feeling.values()[i + 1]
        imageRecyclerViewAdapterViewHolder.itemImage!!.setImageDrawable( feeling.getImage(context))
        imageRecyclerViewAdapterViewHolder.itemName!!.text = feeling.getName(context)
    }

    override fun getItemCount(): Int {
        //disconsidering None
        return Feeling.values().size - 1
    }
}
