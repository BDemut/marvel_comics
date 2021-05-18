package com.defconapplications.marvel.repository

import androidx.room.TypeConverter
import com.defconapplications.marvel.repository.database.RoomComic
import com.defconapplications.marvel.repository.network.Creator
import com.defconapplications.marvel.repository.network.NetworkComic
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


fun List<NetworkComic>.asRoomComics() : Array<RoomComic> {
    return this.map {
        var urlStr : String? = null
        for (urlData in it.urls) {
            if (urlData.type == "detail") {
                urlStr = urlData.url
            }
        }
        RoomComic(
                id = it.id,
                title = it.title,
                description = it.description,
                url = urlStr,
                creators = it.creators.items,
                thumbnailPath = it.thumbnail.path + '.' + it.thumbnail.extension
        )
    }.toTypedArray()
}

private val gson : Gson by lazy {
    Gson()
}

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Creator?>? {
        return gson.fromJson(value, object : TypeToken<List<Creator?>?>() {}.type)
    }

    @TypeConverter
    fun fromList(list: List<Creator?>?): String? {
        return gson.toJson(list)
    }
}

