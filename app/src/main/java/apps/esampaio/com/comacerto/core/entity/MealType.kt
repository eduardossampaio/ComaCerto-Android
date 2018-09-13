package apps.esampaio.com.comacerto.core.entity

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R

enum class MealType {
    None(R.string.blank,0),
    Breakfast(R.string.meal_breakfast,R.drawable.meal_breakfast),
    Lunch(R.string.meal_lunch,R.drawable.meal_lunch),
    Snack(R.string.meal_snack,R.drawable.meal_snack),
    Dinner(R.string.meal_dinner,R.drawable.meal_dinner);

    val nameId:Int
    val imageId:Int
    val imageDrawable : Drawable?

    constructor(nameId:Int,imageId:Int){
        this.nameId = nameId
        this.imageId = imageId
        imageDrawable = ContextCompat.getDrawable(MyApplication.instance,R.mipmap.ic_launcher)
    }

    fun getImage(context: Context) : Drawable? {
//        return context.resources.getDrawable(imageId)
//        return ContextCompat.getDrawable(context,R.mipmap.ic_launcher)
        return imageDrawable
    }

    fun getName(context: Context) : String {
        return context.resources.getString(nameId)
    }
}