package com.defconapplications.marvel

import com.defconapplications.marvel.repository.network.Creator


fun List<Creator?>?.getCreators(short : Boolean = false) : String {
    val builder = StringBuilder("")
    var first = true
    this?.let {
        if (short) {
            var count = 0
            for (creator in it) {
                creator?.let {
                    if (it.role == "writer") {
                        if (!first) {
                            builder.append(", ")
                        } else
                            first = false
                        builder.append(it.name)
                        count++
                    }
                }
                if (count >= 3) break
            }
        }
        else {
            for (creator in it) {
                creator?.let {
                    if (!first) {
                        builder.append(", ")
                    } else
                        first = false
                    builder.append(it.name)
                }
            }
        }
    }
    return builder.toString()
}

fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}