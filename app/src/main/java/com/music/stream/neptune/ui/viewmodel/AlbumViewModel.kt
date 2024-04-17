package com.music.stream.neptune.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.music.stream.neptune.data.api.Response
import com.music.stream.neptune.data.entity.AlbumsModel
import com.music.stream.neptune.data.entity.SongsModel
import com.music.stream.neptune.ui.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumViewModel @Inject constructor(private val repository: AppRepository) :  ViewModel() {


    private val _songs = MutableStateFlow<List<SongsModel>>(emptyList())
    val songs : StateFlow<List<SongsModel>> = _songs

    private val _albums : MutableStateFlow<Response<List<AlbumsModel>>> = MutableStateFlow(Response.Loading())
    val albums : StateFlow<Response<List<AlbumsModel>>> = _albums


    init {
        fetchAlbums()
        fetchSongs()
    }

    private fun fetchSongs() = viewModelScope.launch(Dispatchers.IO) {

        repository.provideSongs().collect {
            _songs.value = it

        }
    }

    private fun fetchAlbums() = viewModelScope.launch(Dispatchers.IO) {
        repository.provideAlbums().collect{ album ->
            _albums.value = album as Response<List<AlbumsModel>>
        }
    }
}