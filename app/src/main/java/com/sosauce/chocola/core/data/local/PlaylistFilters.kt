package com.sosauce.chocola.core.data.local

import com.sosauce.chocola.core.domain.util.regex
import com.sosauce.chocola.core.domain.util.thenIf
import com.sosauce.chocola.core.domain.model.PlaylistSort

fun List<Playlist>.ordered(
    sort: PlaylistSort,
    regex: Boolean,
    matchCase: Boolean,
    ascending: Boolean,
    query: String
): List<Playlist> {
    val regexPattern = query.regex(matchCase)

    val filtered = this.filter { track ->
        if (regex) {
            regexPattern.containsMatchIn(track.name)
        } else {
            track.name.contains(query, !matchCase)
        }
    }

    return filtered
        .sortedWith(
            compareBy(String.CASE_INSENSITIVE_ORDER) {
                when (sort) {
                    PlaylistSort.NAME -> it.name
                    PlaylistSort.NB_TRACKS -> it.musics.size.toString()
                    PlaylistSort.TAGS -> it.tags.size.toString()
                    PlaylistSort.COLOR -> it.color.toString()
                }
            }
        ).thenIf(!ascending) { asReversed() }
}
