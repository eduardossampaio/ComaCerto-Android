package apps.esampaio.com.comacerto.core.entity

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R

enum class Feeling {
    None(R.string.blank,R.mipmap.ic_launcher),
    Natural(R.string.feeling_natural,R.drawable.feeling_natural_flat),
    Anxiety(R.string.feeling_anxiety,R.drawable.feeling_anxiety_flat),
    Frustration(R.string.feeling_frustration,R.drawable.ic_feeling_frustration),
    Irritation(R.string.feeling_irritation,R.drawable.feeling_irritation_flat),
    Hurry(R.string.feeling_hurry,R.drawable.feeling_hurry_flat),
    Rage(R.string.feeling_rage,R.drawable.feeling_rage_flat),
    Boredom(R.string.feeling_boredom,R.drawable.ic_feeling_boredom),
    Sad(R.string.feeling_sad,R.drawable.ic_feeling_sad);

    private val nameId:Int
    private val imageId:Int
    val imageDrawable : Drawable?

    constructor(nameId:Int,imageId:Int){
        this.nameId = nameId
        this.imageId = imageId
        if ( imageId != 0) {
            imageDrawable = ContextCompat.getDrawable(MyApplication.instance, imageId)
        }else{
            imageDrawable = null
        }
    }

    fun getImage(context: Context) : Drawable? {
        return imageDrawable
    }

    fun getName(context: Context) : String {
        return context.resources.getString(nameId)
    }

    companion object {
        fun getByOrdinal(ordinal: Int): Feeling {
            for (feeling in Feeling.values()) {
                if (feeling.ordinal == ordinal) {
                    return feeling
                }
            }
            return None
        }
    }

}