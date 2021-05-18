package com.defconapplications.marvel.repository.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.defconapplications.marvel.repository.network.Creator
import kotlinx.parcelize.Parcelize

@Entity
data class RoomComic(
        @PrimaryKey
        val id : Int,
        val title : String,
        val description : String?,
        val url : String?,
        val creators : List<Creator?>?,
        val thumbnailPath: String)

