@file:OptIn(FlowPreview::class)

package com.sosauce.chocola.feature.library.presentation.main

import android.app.Application
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sosauce.chocola.core.data.library.AbstractTracksScanner
import com.sosauce.chocola.core.data.datastore.UserPreferences
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

class MainViewModel(
    private val application: Application,
    private val abstractTracksScanner: AbstractTracksScanner,
    private val userPreferences: UserPreferences
) : AndroidViewModel(application) {

    val textFieldState = TextFieldState()
    private val searchQuery = snapshotFlow { textFieldState.text }.debounce(250.milliseconds)

    val state = combine(
        abstractTracksScanner.latestTracks(),
        userPreferences.searchSettings(),
        searchQuery,
    ) { tracks, searchSettings, query ->
        val searched = tracks.search(query.toString(), searchSettings)
        MainState(
            isLoading = false,
            tracks = searched
        )
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        MainState()
    )
}

@Stable
data class MainState(
    val isLoading: Boolean = true,
    val tracks: List<CuteTrack> = emptyList()
)