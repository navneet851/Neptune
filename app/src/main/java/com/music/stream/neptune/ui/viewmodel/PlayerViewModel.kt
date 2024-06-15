package com.music.stream.neptune.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    val songProgress = mutableStateOf(0f)
    val shuffleState = currentSongState.shuffle
    val repeatState = currentSongState.repeat


    private val _songs : MutableStateFlow<Response<List<SongsModel>>> = MutableStateFlow(Response.Loading())
    val songs : StateFlow<Response<List<SongsModel>>> = _songs



    init {
        fetchSongs()
    }
    //val songsResponse = (songs.value as Response.Success).data

    // Function to play the next song in the album
    fun playNextSongs(queueSongs : List<SongsModel>, context: Context) {
        if (queueSongs.isNotEmpty()) {
            if (currentSongIndex.value < queueSongs.size - 1) {

                val nextSong = queueSongs[currentSongIndex.value + 1]
                updateSongState(
                    nextSong.coverUri,
                    nextSong.title,
                    nextSong.singer,
                    true,
                    (currentSongIndex.value + 1),
                    currentSongAlbum.value
                )
                SongPlayer.playSong(nextSong.url, context)
            } else {
                val nextSong = queueSongs[0]
                updateSongState(
                    nextSong.coverUri,
                    nextSong.title,
                    nextSong.singer,
                    true,
                    0,
                    currentSongAlbum.value
                )
                SongPlayer.playSong(nextSong.url, context)
            }
        }
    }

    // Function to play the previous song in the album
    fun playPreviousSong(queueSongs : List<SongsModel>, context: Context) {
        if (currentSongIndex.value > 0) {
            val previousSong = queueSongs[(currentSongIndex.value-1)]
            updateSongState(previousSong.coverUri, previousSong.title, previousSong.singer, true, (currentSongIndex.value-1), currentSongAlbum.value)
            SongPlayer.playSong(previousSong.url, context)
        }
        else{
            val previousSong = queueSongs[queueSongs.size-1]
            updateSongState(previousSong.coverUri, previousSong.title, previousSong.singer, true, queueSongs.size-1, currentSongAlbum.value)
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

    fun updateShuffleState(shuffleState : Boolean){
        currentSongState.updateShuffleState(shuffleState)
    }
    fun updateRepeatState(repeatState : Boolean){
        currentSongState.updateRepeatState(repeatState)
    }
}