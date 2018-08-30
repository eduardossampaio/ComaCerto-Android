package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.content.Context
import apps.esampaio.com.comacerto.core.entity.Feeling

import apps.esampaio.com.comacerto.core.entity.MealType

class FeelingsListRecyclerViewAdapter(context: Context) : ImageRecyclerViewAdapter(context) {

    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        val felling = Feeling.values()[i + 1]
        //        imageRecyclerViewAdapterViewHolder.itemView = mealType.toString();
        imageRecyclerViewAdapterViewHolder.itemName!!.text = felling.toString()
    }

    override fun getItemCount(): Int {
        //disconsidering None
        return Feeling.values().size - 1
    }
}
