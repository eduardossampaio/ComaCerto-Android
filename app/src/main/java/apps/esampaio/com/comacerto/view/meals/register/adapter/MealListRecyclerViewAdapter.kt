package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context
import android.util.Log
import apps.esampaio.com.comacerto.core.entity.Meal

import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon


open class MealListRecyclerViewAdapter(context: Context,private val meals: Array<MealType> = MealType.defaultMealTypes) : ImageRecyclerViewAdapter(context) {
    var onMealSelectedListener : ((MealType) -> Unit)? = null


    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i)
        val mealType = meals[i]
        imageRecyclerViewAdapterViewHolder.itemImage!!.setImageDrawable( mealType.getIcon())
        imageRecyclerViewAdapterViewHolder.itemName!!.text = mealType.name
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun itemSelectedAtPosition(position:Int){
        Log.d("MealsAdapter", "positions is $position")
        if ( position in 0..meals.size) {
            onMealSelectedListener?.invoke(meals[position])
        }
    }

    fun selectItem(mealType: MealType){
        for(i in meals.indices){
            if(mealType == meals[i]){
                selectedItem = i;
                break;
            }
        }
    }


}
