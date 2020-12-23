package apps.esampaio.com.comacerto.core.entity

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R
import java.io.Serializable

class MealTypeIcon : Serializable {
    companion object {

        val NONE_ICON = MealTypeIcon("NONE_ICON", R.mipmap.ic_launcher)
        val BREAKFAST_ICON = MealTypeIcon("BREAKFAST_ICON", R.drawable.meal_breakfast_flat)
        val LUNCH_ICON = MealTypeIcon("LUNCH_ICON",R.drawable.meal_lunch_flat)
        val SNACK_ICON = MealTypeIcon("SNACK_ICON",R.drawable.meal_snack_flat)
        val CANDY_ICON = MealTypeIcon("CANDY_ICON",R.drawable.meal_candy_flat)
        val DINNER_ICON = MealTypeIcon("DINNER_ICON",R.drawable.meal_dinner_flat);

        val MEAL_EXTRA_ICON_1 = MealTypeIcon("MEAL_EXTRA_ICON_1",R.drawable.ic_meal_icon_extra_1)
        val MEAL_EXTRA_ICON_2 = MealTypeIcon("MEAL_EXTRA_ICON_2",R.drawable.ic_meal_icon_extra_2)
        val MEAL_EXTRA_ICON_3 = MealTypeIcon("MEAL_EXTRA_ICON_3",R.drawable.ic_meal_icon_extra_3)
        val MEAL_EXTRA_ICON_4 = MealTypeIcon("MEAL_EXTRA_ICON_4",R.drawable.ic_meal_icon_extra_4);

        val defaultMealIcons = arrayOf(
                BREAKFAST_ICON,
                LUNCH_ICON,
                SNACK_ICON,
                CANDY_ICON,
                DINNER_ICON);
        val extraMealIcons = arrayOf(
                MEAL_EXTRA_ICON_1,
                MEAL_EXTRA_ICON_2,
                MEAL_EXTRA_ICON_3,
                MEAL_EXTRA_ICON_4);

        fun allMealIcons () :Array<MealTypeIcon> {
            return defaultMealIcons.copyOf().plus(extraMealIcons)
        }

        fun getMealIcon(name:String) : MealTypeIcon{
            for(mealTypeIcon in allMealIcons()){
                if(mealTypeIcon.iconName == name){
                    return mealTypeIcon;
                }
            }
            return NONE_ICON;
        }


    }


    val iconName: String;
    val iconDrawableResource: Int;

    constructor(iconName: String, iconDrawableResource: Int) {
        this.iconName = iconName
        this.iconDrawableResource = iconDrawableResource
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealTypeIcon

        if (iconName != other.iconName) return false

        return true
    }

    override fun hashCode(): Int {
        return iconName.hashCode()
    }


}