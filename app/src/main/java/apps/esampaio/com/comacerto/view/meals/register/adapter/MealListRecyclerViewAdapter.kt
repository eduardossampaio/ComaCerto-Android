package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context
import android.util.Log
import apps.esampaio.com.comacerto.core.entity.Meal

import apps.esampaio.com.comacerto.core.entity.MealType



class MealListRecyclerViewAdapter(context: Context) : ImageRecyclerViewAdapter(context) {
    var onMealSelectedListener : ((MealType) -> Unit)? = null
    var meals = MealType.values().drop(1)

    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i)
        val mealType = meals[i]
        imageRecyclerViewAdapterViewHolder.itemImage!!.setImageDrawable( mealType.getImage(context))
        imageRecyclerViewAdapterViewHolder.itemName!!.text = mealType.getName(context)
    }

    override fun getItemCount(): Int {
        //disconsidering None
        return meals.size
    }

    override fun itemSelectedAtPosition(position:Int){
        Log.d("MealsAdapter", "positions is ${position}")
        if ( position in 0..meals.size) {
            onMealSelectedListener?.invoke(meals[position])
        }
    }


}
