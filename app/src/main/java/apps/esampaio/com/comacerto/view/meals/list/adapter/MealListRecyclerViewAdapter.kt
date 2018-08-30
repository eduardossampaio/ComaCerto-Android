package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.content.Context

import apps.esampaio.com.comacerto.core.entity.MealType

class MealListRecyclerViewAdapter(context: Context) : ImageRecyclerViewAdapter(context) {

    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        val mealType = MealType.values()[i + 1]
        //        imageRecyclerViewAdapterViewHolder.itemView = mealType.toString();
        imageRecyclerViewAdapterViewHolder.itemName!!.text = mealType.toString()
    }

    override fun getItemCount(): Int {
        //disconsidering None
        return MealType.values().size - 1
    }
}
