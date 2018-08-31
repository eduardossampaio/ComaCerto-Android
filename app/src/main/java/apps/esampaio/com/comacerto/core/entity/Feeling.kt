package apps.esampaio.com.comacerto.core.entity

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import apps.esampaio.com.comacerto.R

enum class Feeling {
    None(0,0),
    Natural(R.string.feeling_natural,R.drawable.feeling_normal),
    Anxiety(R.string.feeling_anxiety,R.drawable.feeling_anxiety),
    Frustration(R.string.feeling_frustration,R.drawable.feeling_frustration),
    Irritation(R.string.feeling_irritation,R.drawable.feeling_irritation),
    Hurry(R.string.feeling_hurry,R.drawable.feeling_hurry),
    Rage(R.string.feeling_rage,R.drawable.feeling_rage),
    Boredom(R.string.feeling_boredom,R.drawable.feeling_boredom),
    Sad(R.string.feeling_sad,R.drawable.feeling_sad);

    private val nameId:Int
    private val imageId:Int

    constructor(nameId:Int,imageId:Int){
        this.nameId = nameId
        this.imageId = imageId
    }

    fun getImage(context: Context) : Drawable? {
        return ContextCompat.getDrawable(context,imageId)
    }

    fun getName(context: Context) : String {
        return context.resources.getString(nameId)
    }

}