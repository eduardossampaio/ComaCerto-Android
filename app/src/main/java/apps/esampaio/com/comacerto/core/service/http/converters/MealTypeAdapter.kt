package apps.esampaio.com.comacerto.core.service.http.converters

import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.core.entity.Feeling
import apps.esampaio.com.comacerto.core.entity.MealType
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class MealTypeAdapter : JsonSerializer<MealType> {
    override fun serialize(src: MealType, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        return  context.serialize(src.getName(MyApplication.instance), String::class.java)
    }

}