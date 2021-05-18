package com.defconapplications.marvel.repository

import com.defconapplications.marvel.repository.network.Creator
import com.defconapplications.marvel.repository.network.Thumbnail

/**
 * Domain model
 */
data class Comic (
        val id : Int,
        val title: String,
        val description: String?,
        val url : String?,
        val creators : List<Creator?>?,
        val thumbnailPath: String,
)