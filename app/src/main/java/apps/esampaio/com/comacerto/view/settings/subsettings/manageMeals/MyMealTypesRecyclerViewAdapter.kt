package apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.MealType

class MyMealTypesRecyclerViewAdapter(private var mealTypeList: List<MealType>) : RecyclerView.Adapter<MyMealTypesRecyclerViewAdapter.ViewHolder>() {

    var deleteListener : ((MealType) -> Unit)? = null;

//    fun setDeleteListener(deleteListener: DeleteListener?){
//        this.deleteListener = deleteListener;
//    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mealName : TextView =  view.findViewById(R.id.meal_name)
        val mealImage : ImageView =  view.findViewById(R.id.meal_image)
        val deleteButton : ImageButton = view.findViewById(R.id.delete_meal_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_add_meal_view_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mealType = mealTypeList[position]
        holder.mealName.text = mealType.name
        holder.mealImage.setImageResource(mealType.mealTypeIcon.iconDrawableResource)
        holder.deleteButton.setOnClickListener {
            if(deleteListener != null) {
                deleteListener!!(mealType)
            }
        }
    }

    fun update(mealTypeList: List<MealType>){
        this.mealTypeList = mealTypeList;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mealTypeList.size
    }
}