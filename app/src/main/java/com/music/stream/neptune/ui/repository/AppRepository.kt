package com.music.stream.neptune.ui.repository

import com.music.stream.neptune.data.api.Api
import javax.inject.Inject

class AppRepository @Inject constructor(private val api : Api) {

    suspend fun provideAlbums() = api.getAlbums()

    suspend fun provideArtists() = api.getArtists()

    suspend fun provideSongs() = api.getSongs()
}