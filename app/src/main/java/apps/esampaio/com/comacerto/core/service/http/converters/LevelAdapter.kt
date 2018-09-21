package apps.esampaio.com.comacerto.core.service.http.converters

import apps.esampaio.com.comacerto.core.entity.Level
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LevelAdapter : JsonSerializer<Level> {
    override fun serialize(src: Level?, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        var levelValue = ""
        if ( src != null){
            val level = src as Level
            levelValue = level.levelNames[level.level]
        }
        return  context.serialize(levelValue, String::class.java)
    }


}