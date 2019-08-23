package apps.esampaio.com.comacerto.core.entity

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.R

enum class Feeling {
    None(R.string.blank,R.mipmap.ic_launcher),
    Natural(R.string.feeling_natural,R.drawable.ic_feeling_natural),
    Anxiety(R.string.feeling_anxiety,R.drawable.ic_feeling_anxious),
    Frustration(R.string.feeling_frustration,R.drawable.ic_feeling_frustration),
    Irritation(R.string.feeling_irritation,R.drawable.ic_feeling_irritation),
    Hurry(R.string.feeling_hurry,R.drawable.ic_feeling_hurry),
    Rage(R.string.feeling_rage,R.drawable.ic_feeling_rage),
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