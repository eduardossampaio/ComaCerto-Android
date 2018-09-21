package apps.esampaio.com.comacerto.core.service.http.converters

import apps.esampaio.com.comacerto.MyApplication
import apps.esampaio.com.comacerto.core.entity.Feeling
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class FeelingAdapter : JsonSerializer<Feeling> {
    override fun serialize(src: Feeling, typeOfSrc: Type?, context: JsonSerializationContext): JsonElement {
        return  context.serialize(src.getName(MyApplication.instance), String::class.java)
    }

}