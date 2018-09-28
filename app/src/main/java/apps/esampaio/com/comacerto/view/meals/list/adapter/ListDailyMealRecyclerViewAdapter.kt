package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.*
import apps.esampaio.com.comacerto.core.extensions.asString
import apps.esampaio.com.comacerto.view.meals.edit.EditMealActivity

class ListDailyMealRecyclerViewAdapter(val context: Context, var mealList:List<Meal>) : RecyclerView.Adapter<ListDailyMealRecyclerViewAdapter.ListDailyMealAdapterViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListDailyMealAdapterViewHolder {
        return ListDailyMealAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.list_daily_meal_view_item,null,false))
    }

    override fun getItemCount(): Int {
        return mealList.size;
    }

    override fun onBindViewHolder(viewHolder: ListDailyMealAdapterViewHolder, index: Int) {
        val meal = mealList.get(index)
        viewHolder.mealName.text = meal.mealType.getName(context)
        setMealIcon(viewHolder,meal)
        setFoodsQuantityText(viewHolder,meal)

        viewHolder.mealHour.text = meal.date.asString("HH:mm")
        viewHolder.view.setOnClickListener{
            val intent = Intent(context, EditMealActivity::class.java)
            intent.putExtra(EditMealActivity.MEAL_INTENT_PARAM,meal);
            context.startActivity(intent)
        }
    }

    fun updateItems(newItems:List<Meal>){
        if(isEquals(newItems)){

        }else{
            this.mealList = newItems
            notifyDataSetChanged()
        }
    }

    private fun isEquals(newItems:List<Meal>): Boolean{
        if(newItems.size != mealList.size) {
            return false
        }

        for (newMeal in newItems){
            if (mealList.contains(newMeal) == false){
                return false
            }
        }
        return true
    }

    private fun setMealIcon(viewHolder: ListDailyMealAdapterViewHolder, meal: Meal){
        val previousIconSettedId = viewHolder.iconImage.tag as Int?
        if (previousIconSettedId != null && previousIconSettedId == meal.mealType.ordinal){

        }else{
            viewHolder.iconImage.tag = meal.mealType.ordinal
            viewHolder.iconImage.setImageDrawable(meal.mealType.getImage(context))
        }
    }

    private fun setFoodsQuantityText(viewHolder: ListDailyMealAdapterViewHolder, meal: Meal){
        val foodsCount = meal.foods.size

        if(foodsCount == 0){
            viewHolder.foodCount.text = context.getString(R.string.no_foods_registered)
            viewHolder.foodCount.setTextColor(ContextCompat.getColor(context,R.color.text_error))
        }else {
            viewHolder.foodCount.text = "${foodsCount} ${context.resources.getQuantityString(R.plurals.foods_registered,foodsCount)}"
            viewHolder.foodCount.setTextColor(ContextCompat.getColor(context,R.color.primary))
        }
    }

    class ListDailyMealAdapterViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val iconImage:ImageView
        val mealName:TextView
        val foodCount:TextView
        val mealHour:TextView
        val view:View
        init{
            this.view = view
            iconImage = view.findViewById(R.id.meal_image)
            mealName = view.findViewById(R.id.meal_name)
            foodCount = view.findViewById(R.id.food_count)
            mealHour = view.findViewById(R.id.meal_hour)

        }
    }

}