package apps.esampaio.com.comacerto.view.meals.register.adapter

import android.arch.persistence.room.util.StringUtil
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Food
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton

class ListFoodRecyclerViewAdapter2(val context: Context,var foodsList:MutableList<Food> = mutableListOf()) : RecyclerView.Adapter<ListFoodRecyclerViewAdapter2.ListFoodRecyclerViewHolder>() {

    lateinit var recyclerView: RecyclerView
    val allFoodsList:List<Food>

    init {
        allFoodsList = foodsList
    }

    class ListFoodRecyclerViewHolder : RecyclerView.ViewHolder {
        val foodNameTextView:TextView
        val foodCategoryTextView:TextView
        val portionTextView:TextView
        val plusOneButton:ImageView
        val minusOneButton:ImageView

        constructor(view: View) : super(view) {
            foodNameTextView = view.findViewById(R.id.food_name_text_view)
            foodCategoryTextView = view.findViewById(R.id.food_category_text_view)
            portionTextView = view.findViewById(R.id.food_quantity_text_view)
            plusOneButton = view.findViewById(R.id.plus_one_button)
            minusOneButton = view.findViewById(R.id.minus_one_button)
        }

        fun updateViews(food: Food){
            portionTextView.text = "${food.portion}"
            if (food.portion >=1){
                minusOneButton.visibility = View.VISIBLE
            }else{
                minusOneButton.visibility = View.INVISIBLE
            }
        }

    }

    fun filterItems(searchText: String?){
        if (TextUtils.isEmpty(searchText)){
            this.foodsList = allFoodsList.toMutableList()
        }else {
            val filteredList = mutableListOf<Food>()
            val food = Food(searchText!!,context.getString(R.string.my_foods),portion = 0)
            filteredList.add(food)
            for(food in allFoodsList){
                if ( food.name.startsWith(searchText!! ,ignoreCase = true) || food.category.startsWith(searchText ,ignoreCase = true)){
                    filteredList.add(food)
                }
            }
            this.foodsList = filteredList
        }
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }


    override fun onBindViewHolder(viewHolder: ListFoodRecyclerViewHolder, index: Int) {
        val food = foodsList.get(index)
        viewHolder.foodNameTextView.text = food.name
        viewHolder.foodCategoryTextView.text = food.category
        viewHolder.plusOneButton.setOnClickListener {
            food.portion++
            viewHolder.updateViews(food)
        }
        viewHolder.minusOneButton.setOnClickListener {
            food.portion--
            viewHolder.updateViews(food)
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

    fun getSelectedFoods() : List<Food>{
        val selectedFoodsList = mutableListOf<Food>()
        for(food in foodsList){
            if ( food.portion > 0){
                selectedFoodsList.add(food)
            }
        }
        return selectedFoodsList
    }
//    fun addFood(foodName: String){
//        val food = Food(foodName,context.getString(R.string.my_foods))
//        food.portion = 1
//        foodsList.add(0, food)
//        notifyItemInserted(0)
//        notifyItemRangeChanged(0, getItemCount());
//        recyclerView.scrollToPosition(0)
//    }

    fun removeAll(){
        foodsList.clear()
        notifyDataSetChanged()
    }
}