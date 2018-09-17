package apps.esampaio.com.comacerto.core.persistence.converters

import android.arch.persistence.room.TypeConverter
import android.content.Intent

import java.util.Date

import apps.esampaio.com.comacerto.core.entity.Level

object LevelConverter {
    @TypeConverter
    fun toLevel(value: Int?): Level? {
        return if (value == null) null else Level.hunger(value)
    }

    @TypeConverter
    fun toInt(value: Level?): Int? {
        return value?.level
    }
}
