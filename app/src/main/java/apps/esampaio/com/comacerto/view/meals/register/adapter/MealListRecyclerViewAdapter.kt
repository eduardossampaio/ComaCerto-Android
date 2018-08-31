package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context

import apps.esampaio.com.comacerto.core.entity.MealType

class MealListRecyclerViewAdapter(context: Context) : ImageRecyclerViewAdapter(context) {

    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i)
        val mealType = MealType.values()[i + 1]
        imageRecyclerViewAdapterViewHolder.itemImage!!.setImageDrawable( mealType.getImage(context))
        imageRecyclerViewAdapterViewHolder.itemName!!.text = mealType.getName(context)
    }

    override fun getItemCount(): Int {
        //disconsidering None
        return MealType.values().size - 1
    }
}
