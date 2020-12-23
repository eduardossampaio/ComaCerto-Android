package apps.esampaio.com.comacerto.core.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import apps.esampaio.com.comacerto.core.entity.MealType
import apps.esampaio.com.comacerto.core.entity.MealTypeIcon

@Entity
class MealTypeEntity() {


    @PrimaryKey(autoGenerate = true)
    var primaryKey : Long? = null

    var name: String = ""

    var iconName: String = ""

    constructor(name: String, iconName: String) : this() {
        this.name = name
        this.iconName = iconName
    }
    constructor(primaryKey: Long?, name: String, iconName: String) : this() {
        this.primaryKey = primaryKey
        this.name = name
        this.iconName = iconName
    }

    fun toMealType(): MealType? {
        val mealType = primaryKey?.let { MealType(it.toInt(),name, MealTypeIcon.getMealIcon(iconName)) };
        return mealType
    }
}