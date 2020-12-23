package apps.esampaio.com.comacerto.view.settings.subsettings.manageMeals

import android.content.Context
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.Meal
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon
import apps.esampaio.com.comacerto.view.meals.register.adapter.ImageRecyclerViewAdapter
//creditos
//https://www.iconfinder.com/Falara
//https://www.iconfinder.com/sorasak21
//https://www.iconfinder.com/Manthana
class MealIconRecyclerViewAdapter(context: Context) : ImageRecyclerViewAdapter(context) {

    private val iconList = MealTypeIcon.allMealIcons();

    override fun getItemCount(): Int {
        return iconList.size;
    }
    private var selectedIcon = iconList[0]

    override fun onBindViewHolder(imageRecyclerViewAdapterViewHolder: ImageRecyclerViewAdapter.ImageRecyclerViewAdapterViewHolder, i: Int) {
        super.onBindViewHolder(imageRecyclerViewAdapterViewHolder, i)
        val icon = iconList[i];
        imageRecyclerViewAdapterViewHolder.itemImage!!.setImageResource(icon.iconDrawableResource)
        imageRecyclerViewAdapterViewHolder.itemName!!.text = ""
    }
    override fun itemSelectedAtPosition(position:Int){
        selectedIcon = iconList[position]
    }

    fun getSelectedIcon() : MealTypeIcon{
        return selectedIcon;
    }
}