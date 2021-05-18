package com.defconapplications.marvel.repository.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

data class Response(
        val data: Data
)

data class Data(
        val results : List<NetworkComic>
)

data class Thumbnail(
        val path : String,
        val extension : String
)

data class Url(
        val type : String,
        val url : String
)

data class Creators (
        val items : List<Creator?>?
)

data class Creator (
        val name : String,
        val role : String
)

data class NetworkComic(
        val id : Int,
        val title : String,
        val description : String?,
        val urls : List<Url>,
        val creators : Creators,
        val thumbnail: Thumbnail
        )
