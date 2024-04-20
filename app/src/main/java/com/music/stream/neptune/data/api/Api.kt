package com.music.stream.neptune.data.api

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.music.stream.neptune.data.entity.AlbumsModel
import com.music.stream.neptune.data.entity.ArtistsModel
import com.music.stream.neptune.data.entity.SongsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Api @Inject constructor(){
    private val firestore: FirebaseFirestore = Firebase.firestore


     suspend fun getAlbums(): Flow<Response<List<AlbumsModel>>> {
        return flow {
            emit(Response.Loading())
            val snapshot = firestore.collection("albums")
                .whereGreaterThan("id", -1)
                .orderBy("id")
                .get().await()
            val albums = snapshot.documents.mapNotNull { it.toObject(AlbumsModel::class.java) }
            delay(200)
            emit(Response.Success(albums))

        }
    }


    suspend fun getArtists(): Flow<Response<List<ArtistsModel>>> {
        return flow {
            emit(Response.Loading())
            val snapshot = firestore.collection("artists").get().await()
            val artists = snapshot.documents.mapNotNull { it.toObject(ArtistsModel::class.java) }
            Log.d("getting", artists.toString())
            emit(Response.Success(artists))

        }
    }


    suspend fun getSongs(): Flow<Response<List<SongsModel>>> {
        return flow {
            emit(Response.Loading())
            val snapshot = firestore.collection("songs").get().await()
            val songs = snapshot.documents.mapNotNull { it.toObject(SongsModel::class.java) }
            Log.d("checkuu", songs.toString())
            //delay(2000)
            emit(Response.Success(songs))

        }
    }
}