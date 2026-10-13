@file:OptIn(FlowPreview::class)

package com.sosauce.chocola.feature.library.presentation.artist

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.util.fastFilter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosauce.chocola.core.data.library.AbstractTracksScanner
import com.sosauce.chocola.core.data.datastore.UserPreferences
import com.sosauce.chocola.core.domain.model.Album
import com.sosauce.chocola.core.domain.model.Artist
import com.sosauce.chocola.core.domain.model.CuteTrack
import com.sosauce.chocola.core.domain.library.search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.milliseconds

class ArtistDetailsViewModel(
    private val artistName: String,
    private val userPreferences: UserPreferences,
    private val abstractTracksScanner: AbstractTracksScanner
) : ViewModel() {

    val textFieldState = TextFieldState()

    private val searchQuery = snapshotFlow { textFieldState.text }.debounce(250.milliseconds)


    val state = combine(
        abstractTracksScanner.latestTracks(),
        userPreferences.searchSettings(),
        searchQuery
    ) { tracks, settings, query ->
        val searched = tracks
            .fastFilter { it.artist == artistName }
            .search(query.toString(), settings)

        val albums = searched.groupBy { it.album }
            .map { (albumName, tracks) ->
                Album(
                    name = albumName,
                    artist = artistName,
                    tracks = tracks
                )
            }



        ArtistDetailsState(
            isLoading = false,
            artist = Artist(
                name = artistName
            ),
            tracks = searched,
            albums = albums
        )
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ArtistDetailsState()
    )


}

@Stable
data class ArtistDetailsState(
    val isLoading: Boolean = true,
    val artist: Artist = Artist(),
    val tracks: List<CuteTrack> = emptyList(),
    val albums: List<Album> = emptyList()
)