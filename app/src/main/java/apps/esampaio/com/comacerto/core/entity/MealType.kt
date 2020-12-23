package apps.esampaio.com.comacerto.core.entity

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon.Companion.BREAKFAST_ICON
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon.Companion.CANDY_ICON
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon.Companion.DINNER_ICON
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon.Companion.LUNCH_ICON
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon.Companion.NONE_ICON
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon.Companion.SNACK_ICON
import java.io.Serializable

data class MealType( val mealId: Int,
                     val name: String,
                     val mealTypeIcon: MealTypeIcon) : Serializable {


    companion object {

        lateinit var None: MealType
        lateinit var Breakfast: MealType
        lateinit var Lunch: MealType
        lateinit var Snack: MealType
        lateinit var Candies: MealType
        lateinit var Dinner: MealType

        lateinit var defaultMealTypes: Array<MealType>

        fun setup(context: Context) {

            None = MealType(0, context.resources.getString(R.string.blank),NONE_ICON);
            Breakfast = MealType(1, context.resources.getString(R.string.meal_breakfast),BREAKFAST_ICON);
            Lunch = MealType(2, context.resources.getString(R.string.meal_lunch),LUNCH_ICON);
            Snack = MealType(3, context.resources.getString(R.string.meal_snack),SNACK_ICON);
            Candies = MealType(4, context.resources.getString(R.string.meal_candies),CANDY_ICON);
            Dinner = MealType(5, context.resources.getString(R.string.meal_dinner),DINNER_ICON);

            defaultMealTypes = arrayOf(Breakfast, Lunch, Snack, Candies, Dinner)
        }

        fun getDefaultMealType(id: Int): MealType {
            for (mealType in defaultMealTypes) {
                if (mealType.mealId == id) {
                    return mealType
                }
            }
            return None
        }
        fun isDefaultMealType(mealTypeId: Int): Boolean {
            return getDefaultMealType(mealTypeId) != None;
        }
        fun isDefaultMealType(mealType: MealType): Boolean {
            return defaultMealTypes.contains(mealType)
        }
    }


    constructor(name: String, mealTypeIcon: MealTypeIcon) : this(0,name,mealTypeIcon) {

    }

    fun getIcon(): Drawable? {
        val imageId = mealTypeIcon.iconDrawableResource;
        return ContextCompat.getDrawable(MyApplication.instance, imageId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealType

        if (mealId != other.mealId) return false
        if (name != other.name) return false
        if (mealTypeIcon != other.mealTypeIcon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mealId
        result = 31 * result + name.hashCode()
        return result
    }


}