package com.music.stream.neptune.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.music.stream.neptune.data.api.Response
import com.music.stream.neptune.data.entity.SongsModel
import com.music.stream.neptune.di.CurrentSongState
import com.music.stream.neptune.di.SongPlayer
import com.music.stream.neptune.ui.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val currentSongState: CurrentSongState, private val repository: AppRepository) : ViewModel(){

    val currentSongTitle: State<String> get() = currentSongState.title
    val currentSongSinger: State<String> get() = currentSongState.singer
    val currentSongCoverUri: State<String> get() = currentSongState.coverUri
    val currentSongPlayingState: State<Boolean> get() = currentSongState.playingState
    val currentSongIndex : State<Int> get() = currentSongState.songIndex

    val currentSongAlbum : State<String> get() = currentSongState.album

    private val _songs : MutableStateFlow<Response<List<SongsModel>>> = MutableStateFlow(Response.Loading())
    val songs : StateFlow<Response<List<SongsModel>>> = _songs

    init {
        fetchSongs()
    }
    //val songsResponse = (songs.value as Response.Success).data

    // Function to play the next song in the album
    fun playNextSongs(queueSongs : List<SongsModel>, context: Context) {
        if (currentSongIndex.value < queueSongs.size - 1) {

            val nextSong = queueSongs[currentSongIndex.value+1]
            SongPlayer.playSong(nextSong.url, context)
            updateSongState(nextSong.coverUri, nextSong.title, nextSong.singer, true, (currentSongIndex.value + 1))
        }
    }

    // Function to play the previous song in the album
    fun playPreviousSong(queueSongs : List<SongsModel>, context: Context) {
        if (currentSongIndex.value > 0) {
            val previousSong = queueSongs[(currentSongIndex.value-1)]
            updateSongState(previousSong.coverUri, previousSong.title, previousSong.singer, true, (currentSongIndex.value-1))
            SongPlayer.playSong(previousSong.url, context)
        }
    }

    private fun fetchSongs() = viewModelScope.launch(Dispatchers.IO) {

        repository.provideSongs().collect { songs ->
            _songs.value = songs as Response<List<SongsModel>>

        }
    }
    fun formatDuration(durationMillis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%01d:%02d", minutes, seconds)
    }
    fun updateSongState(coverUri: String, title: String, singer: String, playingState: Boolean, songIndex : Int = 0, album : String = "") {
        currentSongState.updateSongState(coverUri, title, singer, playingState, songIndex, album)
    }
}