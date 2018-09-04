package apps.esampaio.com.comacerto.view.meals.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.*
import java.util.*

class ListDailyMealAdapter(val context: Context,var mealList:List<Meal>) : RecyclerView.Adapter<ListDailyMealAdapter.ListDailyMealAdapterViewHolder>() {

    init{

//        mealList = listOf(
//                Meal("",MealType.Breakfast,"",Date(), listOf(), Level.hunger(), Level.satiety(),Feeling.Natural),
//                Meal("",MealType.Lunch,"",Date(), listOf(), Level.hunger(), Level.satiety(),Feeling.Natural),
//                Meal("",MealType.Snack,"",Date(), listOf(), Level.hunger(), Level.satiety(),Feeling.Natural),
//                Meal("",MealType.Dinner,"",Date(), listOf(), Level.hunger(), Level.satiety(),Feeling.Natural))
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListDailyMealAdapterViewHolder {
        return ListDailyMealAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.list_daily_meal_view_item,null,false))
    }

    override fun getItemCount(): Int {
        return mealList.size;
    }

    override fun onBindViewHolder(viewHolder: ListDailyMealAdapterViewHolder, index: Int) {
        val meal = mealList.get(index)
        viewHolder.mealName.text = meal.mealType.getName(context)
        viewHolder.iconImage.setImageDrawable(meal.mealType.getImage(context))
    }

    class ListDailyMealAdapterViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val iconImage:ImageView
        val mealName:TextView
        init{
            iconImage = view.findViewById(R.id.meal_image)
            mealName = view.findViewById(R.id.meal_name)
        }
    }

}