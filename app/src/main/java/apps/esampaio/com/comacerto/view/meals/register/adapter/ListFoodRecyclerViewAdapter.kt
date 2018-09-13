package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Food
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton

class ListFoodRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<ListFoodRecyclerViewAdapter.ListFoodRecyclerViewHolder>() {

    var foodsList = mutableListOf<Food>()

    class ListFoodRecyclerViewHolder : RecyclerView.ViewHolder {
        val foodNameTextView:TextView
        val portionButton:ElegantNumberButton

        constructor(view: View) : super(view) {

            foodNameTextView = view.findViewById(R.id.foodNameTextView)
            portionButton = view.findViewById(R.id.foodPortion)
        }
    }

    override fun onBindViewHolder(viewHolder: ListFoodRecyclerViewHolder, index: Int) {
        val food = foodsList.get(index)
        viewHolder.foodNameTextView.text = food.name
        viewHolder.portionButton.number = "${food.portion}"

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListFoodRecyclerViewHolder {
        return ListFoodRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.list_foods_recycler_view_item, null, false));
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    fun addFood(foodName: String){
        foodsList.add(Food(foodName,"Meus Alimentos"))
        notifyDataSetChanged()
    }


}