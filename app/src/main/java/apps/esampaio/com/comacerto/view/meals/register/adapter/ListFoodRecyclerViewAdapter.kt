package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Food
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton

class ListFoodRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<ListFoodRecyclerViewAdapter.ListFoodRecyclerViewHolder>() {

    var foodsList = mutableListOf<Food>()
    lateinit var recyclerView: RecyclerView
    class ListFoodRecyclerViewHolder : RecyclerView.ViewHolder {
        val foodNameTextView:TextView
        val portionButton:ElegantNumberButton
        val removeButton:ImageView

        constructor(view: View) : super(view) {
            foodNameTextView = view.findViewById(R.id.foodNameTextView)
            portionButton = view.findViewById(R.id.foodPortion)
            removeButton = view.findViewById(R.id.icon_remove)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }


    override fun onBindViewHolder(viewHolder: ListFoodRecyclerViewHolder, index: Int) {
        val food = foodsList.get(index)
        viewHolder.foodNameTextView.text = food.name
        viewHolder.portionButton.number = "${food.portion}"
        viewHolder.portionButton.setOnValueChangeListener { view, oldValue, newValue ->
            food.portion = newValue
        }
        viewHolder.removeButton.setOnClickListener {
            foodsList.removeAt(index)
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ListFoodRecyclerViewHolder {
        return ListFoodRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.list_foods_recycler_view_item, parent, false));
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    fun addFood(foodName: String){
        val food = Food(foodName,"Meus Alimentos")
        if ( foodsList.isEmpty()){
            foodsList.add(food)
        }else {
            foodsList.add(0, food)
        }
        notifyItemInserted(0)
        recyclerView.scrollToPosition(0)

    }


}