package apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.databinding.ListAddMealViewItemBinding

class MyMealTypesRecyclerViewAdapter(private var mealTypeList: List<MealType>) : RecyclerView.Adapter<MyMealTypesRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mealName : TextView =  view.findViewById(R.id.meal_name)
        val mealImage : ImageView =  view.findViewById(R.id.meal_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_add_meal_view_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mealType = mealTypeList[position]
        holder.mealName.text = mealType.name
        holder.mealImage.setImageResource(mealType.mealTypeIcon.iconDrawableResource)
    }

    fun update(mealTypeList: List<MealType>){
        this.mealTypeList = mealTypeList;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mealTypeList.size
    }
}