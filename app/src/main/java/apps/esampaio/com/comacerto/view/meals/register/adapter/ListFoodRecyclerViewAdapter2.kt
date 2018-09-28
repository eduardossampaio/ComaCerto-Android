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

class ListFoodRecyclerViewAdapter2(val context: Context) : RecyclerView.Adapter<ListFoodRecyclerViewAdapter2.ListFoodRecyclerViewHolder>() {

    var foodsList = mutableListOf<Food>()
    lateinit var recyclerView: RecyclerView

    class ListFoodRecyclerViewHolder : RecyclerView.ViewHolder {
        val foodNameTextView:TextView
        val portionTextView:TextView
        val plusOneButton:ImageView
        val minusOneButton:ImageView

        constructor(view: View) : super(view) {
            foodNameTextView = view.findViewById(R.id.food_name_text_view)
            portionTextView = view.findViewById(R.id.food_quantity_text_view)
            plusOneButton = view.findViewById(R.id.plus_one_button)
            minusOneButton = view.findViewById(R.id.minus_one_button)

        }

        fun updateViews(food:Food){
            portionTextView.text = "${food.portion}"
            if(food.portion <= 1){
                minusOneButton.setImageResource(R.drawable.ic_delete)
            }else{
                minusOneButton.setImageResource(R.drawable.ic_minus)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }


    override fun onBindViewHolder(viewHolder: ListFoodRecyclerViewHolder, index: Int) {
        val food = foodsList.get(index)
        viewHolder.foodNameTextView.text = food.name
        viewHolder.portionTextView.text = "${food.portion}"
        viewHolder.plusOneButton.setOnClickListener {
            food.portion++
            viewHolder.updateViews(food)

        }
        viewHolder.minusOneButton.setOnClickListener {
            food.portion--
            if(food.portion==0){
                removeItemAt(index)
            }else {
                viewHolder.updateViews(food)
            }
        }
        viewHolder.updateViews(food)
    }
    private fun removeItemAt(index: Int){
        foodsList.removeAt(index)
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, getItemCount());
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ListFoodRecyclerViewHolder {
        return ListFoodRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.list_foods_recycler_view_item_2, parent, false));
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    fun addFood(foodName: String){
        val food = Food(foodName,"Meus Alimentos")
        food.portion = 1
        foodsList.add(0, food)
        notifyItemInserted(0)
        notifyItemRangeChanged(0, getItemCount());
        recyclerView.scrollToPosition(0)
    }

    fun removeAll(){
        foodsList.clear()
        notifyDataSetChanged()
    }
}