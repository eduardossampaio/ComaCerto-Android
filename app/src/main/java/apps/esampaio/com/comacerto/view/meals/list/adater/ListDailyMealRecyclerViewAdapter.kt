package apps.esampaio.com.comacerto.view.meals.list.adater

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
import apps.esampaio.com.comacerto.view.water.ListWaterActivity
import kotlin.collections.ArrayList

class ListDailyMealRecyclerViewAdapter : RecyclerView.Adapter<ListDailyMealRecyclerViewAdapter.ListDailyMealAdapterViewHolder> {

    val ITEM_TYPE_HEADER = 1
    val ITEM_TYPE_NO_HEADER = 2

    val context: Context
    var mealList:List<Meal>
    var waterList:List<Water> = emptyList()

    constructor(context: Context, mealList: List<Meal>,waterList:List<Water> = emptyList()) : super() {
        this.context = context
        this.mealList = mealList
        this.waterList = waterList
    }


    private fun hasWater() : Boolean{
        return waterList!= null && !waterList.isEmpty()
    }
    override fun onCreateViewHolder(viewGroup:  ViewGroup, type: Int): ListDailyMealAdapterViewHolder {
        if ( ITEM_TYPE_HEADER == type){
            return ListDailyMealAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.list_daily_meal_view_item_with_header, null, false))
        }else{
            return ListDailyMealAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.list_daily_meal_view_item, null, false))
        }
    }

    override fun getItemCount(): Int {
        if (!hasWater()) {
            return mealList.size
        }else{
            return mealList.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        if ( position == 0){
            return ITEM_TYPE_HEADER
        }else if( hasWater() && position == mealList.size){
            return ITEM_TYPE_HEADER
        }
        return ITEM_TYPE_NO_HEADER
    }

    override fun onBindViewHolder(viewHolder: ListDailyMealAdapterViewHolder, index: Int) {

        if ( hasWater() && index == mealList.size){
            bindWater(viewHolder,index);
        }else{
            bindMeal(viewHolder,index);
        }
        val headerName:String
        if ( index == 0){
            headerName = context.getString(R.string.meals)
        }else{
            headerName = context.getString(R.string.liquids)
        }
        viewHolder.header?.text = headerName
    }
    private fun bindMeal(viewHolder: ListDailyMealAdapterViewHolder, index: Int){
        val meal = mealList.get(index)
        viewHolder.mealName.text = meal.mealType.getName(context)
        setMealIcon(viewHolder,meal)
        setFoodsQuantityText(viewHolder,meal)

        viewHolder.mealHour.text = meal.date.asString("HH:mm")
        viewHolder.cell.setOnClickListener{
            val intent = Intent(context, EditMealActivity::class.java)
            intent.putExtra(EditMealActivity.MEAL_INTENT_PARAM,meal);
            context.startActivity(intent)
        }
    }
    private fun bindWater(viewHolder: ListDailyMealAdapterViewHolder, index: Int){
        var waterQuantity = 0;
        for (water in waterList){
            waterQuantity += water.quantity
        }
        viewHolder.mealName.text = context.getString(R.string.consumed_water)
        viewHolder.iconImage.setImageResource(R.drawable.ic_water_list)
        viewHolder.foodCount.text = "${context.resources.getQuantityString(R.plurals.water_cups_consumed,waterQuantity,waterQuantity)}"
        viewHolder.mealHour.text = "";
        viewHolder.cell.setOnClickListener{
            val intent =  ListWaterActivity.createIntent(context, ArrayList(waterList))
            context.startActivity(intent)
        }
    }

    fun updateItems(newItems:List<Meal>,newWater:List<Water>){

        if ( ! isEquals(newItems,newWater)){
            this.mealList = newItems
            this.waterList = newWater
        }

    }

    private fun isEquals(newMeals:List<Meal>,newWater: List<Water>): Boolean{
        if(newMeals.size != mealList.size) {
            return false
        }

        if ( newWater.size != waterList.size){
            return false
        }

        for (newMeal in newMeals){
            if (!mealList.contains(newMeal)){
                return false
            }
        }

        for (water in newWater){
            if (!waterList.contains(water)){
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
            viewHolder.foodCount.setTextColor(ContextCompat.getColor(context,R.color.text_error))
        }else {
            viewHolder.foodCount.setTextColor(ContextCompat.getColor(context,R.color.primary))
        }
        viewHolder.foodCount.text = "${context.resources.getQuantityString(R.plurals.foods_registered,foodsCount,foodsCount)}"
    }

    class ListDailyMealAdapterViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val iconImage:ImageView = view.findViewById(R.id.meal_image)
        val mealName:TextView = view.findViewById(R.id.meal_name)
        val foodCount:TextView = view.findViewById(R.id.food_count)
        val mealHour:TextView = view.findViewById(R.id.meal_hour)
        val header:TextView? = view.findViewById(R.id.header)
        val cell : ViewGroup = view.findViewById(R.id.cell)
        val view:View = view
    }

}