package apps.esampaio.com.comacerto.core.entity

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

import java.util.Date

data class Water(var dateAndTime:Date,var quantity:Int,val id: Long = 0L) : Parcelable {

    constructor(parcel: Parcel) : this(
            Date(parcel.readLong()),
            parcel.readInt(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(dateAndTime.time)
        parcel.writeInt(quantity)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Water(dateAndTime=$dateAndTime, quantity=$quantity)"
    }


    companion object CREATOR : Parcelable.Creator<Water> {
        override fun createFromParcel(parcel: Parcel): Water {
            return Water(parcel)
        }

        override fun newArray(size: Int): Array<Water?> {
            return arrayOfNulls(size)
        }
    }



}