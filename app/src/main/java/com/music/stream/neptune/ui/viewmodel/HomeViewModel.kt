package com.music.stream.neptune.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.music.stream.neptune.data.entity.AlbumsModel
import com.music.stream.neptune.ui.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private val _albums = MutableStateFlow<List<AlbumsModel>>(emptyList())
    val albums : StateFlow<List<AlbumsModel>> = _albums

//    private val _artists = MutableStateFlow<List<ArtistsModel>>(emptyList())
//    val artists : StateFlow<List<ArtistsModel>> = _artists
//
//    private val _songs = MutableStateFlow<List<SongsModel>>(emptyList())
//    val songs : StateFlow<List<SongsModel>> = _songs

    init {
//        fetchArtists()
//        fetchSongs()
        fetchAlbums()
    }

    private fun fetchAlbums() = viewModelScope.launch(Dispatchers.IO) {
        repository.provideAlbums().collect{
            _albums.value = it
        }
    }

//    private fun fetchArtists() = viewModelScope.launch(Dispatchers.IO) {
//        repository.provideArtists().collect {
//            _artists.value = it
//        }
//    }
//
//    private fun fetchSongs() = viewModelScope.launch(Dispatchers.IO) {
//        repository.provideSongs().collect {
//            _songs.value = it
//        }
//    }

}