package com.music.stream.neptune.ui.viewmodel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.music.stream.neptune.di.CurrentSongState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val currentSongState: CurrentSongState) : ViewModel(){

    val currentSongTitle: State<String> get() = currentSongState.title
    val currentSongSinger: State<String> get() = currentSongState.singer
    val currentSongCoverUri: State<String> get() = currentSongState.coverUri
    val currentSongPlayingState: State<Boolean> get() = currentSongState.playingState


    fun updateSongState(coverUri: String, name: String, singer: String, playingState: Boolean) {
        currentSongState.updateSongState(coverUri, name, singer, playingState)
    }
}