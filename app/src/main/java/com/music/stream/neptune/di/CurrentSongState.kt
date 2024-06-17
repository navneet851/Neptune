package com.music.stream.neptune.di

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentSongState @Inject constructor() {
    private val _title: MutableState<String> = mutableStateOf("")
    val title: State<String> get() = _title

    private val _album: MutableState<String> = mutableStateOf("")
    val album : State<String> get() = _album

    private val _singer: MutableState<String> = mutableStateOf("")
    val singer: State<String> get() = _singer

    private val _coverUri: MutableState<String> = mutableStateOf("")
    val coverUri: State<String> get() = _coverUri

    private val _playingState: MutableState<Boolean> = mutableStateOf(false)
    val playingState: State<Boolean> get() = _playingState

    private val _songIndex: MutableState<Int> = mutableStateOf(0)
    val songIndex : State<Int> get() = _songIndex

    private val _songId: MutableState<Int> = mutableStateOf(0)
    val songId : State<Int> get() = _songId

    val shuffle = mutableStateOf(false)
    val repeat = mutableStateOf(false)
    val likeState = mutableStateOf(false)
    fun updateShuffleState(newShuffleState: Boolean) {
        shuffle.value = newShuffleState
    }
    fun updateRepeatState(newRepeatState : Boolean){
        repeat.value = newRepeatState
    }

    fun updateLikeState(newLikeState : Boolean){
        likeState.value = newLikeState
    }

    fun updateSongState(coverUri: String, title: String, singer: String, playingState: Boolean, songId : Int, songIndex : Int, album : String) {
        _coverUri.value = coverUri
        _title.value = title
        _album.value = album
        _singer.value = singer
        _playingState.value = playingState
        _songIndex.value = songIndex
        _songId.value = songId
    }
}