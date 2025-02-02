package com.github.andreyasadchy.xtra.model.gql.tag

import com.github.andreyasadchy.xtra.model.helix.tag.Tag
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class TagStreamDataDeserializer : JsonDeserializer<TagStreamDataResponse> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): TagStreamDataResponse {
        val data = mutableListOf<Tag>()
        val dataJson = json.asJsonObject?.getAsJsonObject("data")?.getAsJsonArray("topTags")
        dataJson?.forEach { item ->
            item?.asJsonObject?.let { obj ->
                data.add(Tag(
                    id = obj.get("id")?.takeIf { !it.isJsonNull }?.asString,
                    name = obj.get("localizedName")?.takeIf { !it.isJsonNull }?.asString,
                    scope = obj.get("scope")?.takeIf { !it.isJsonNull }?.asString,
                ))
            }
        }
        return TagStreamDataResponse(data)
    }
}
