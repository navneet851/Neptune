package com.music.stream.neptune.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.music.stream.neptune.data.api.Response
import com.music.stream.neptune.data.entity.AlbumsModel
import com.music.stream.neptune.data.preferences.getAlbumsByIds
import com.music.stream.neptune.data.preferences.getLikedAlbumIds
import com.music.stream.neptune.ui.components.Loader
import com.music.stream.neptune.ui.components.Snackbar
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(modifier: Modifier = Modifier) {



    Scaffold(
        containerColor = Color(AppBackground.toArgb()),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp),
                        text = "Your Library",
                        fontWeight = FontWeight.Bold,
                        color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(AppBackground.toArgb()),
                ),
                modifier = Modifier.height(120.dp)
            )
        }
    ) { padding ->
        val libraryViewModel : HomeViewModel = hiltViewModel()
        val albums by libraryViewModel.albums.collectAsState()

        when(albums){
            is Response.Loading -> {
                Log.d("homeMain", "loading..-albums")
                Loader()
            }

            is Response.Success -> {
                val albumsResponse = (albums as Response.Success).data

                SumUpLibraryScreen(padding, albumsResponse)
            }

            is Response.Error -> {
                Log.d("homeMain", "Error!!-albums")
            }

            else -> {
                Log.d("homeMain", "failed api")
            }
        }

    }



}




@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SumUpLibraryScreen(padding: PaddingValues, albums: List<AlbumsModel>) {

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(padding)
            .background(Color(0xFF0E0E13))
        ) {

            val context = LocalContext.current
            val libraryAlbumsIds = getLikedAlbumIds(context)
            val libraryAlbums = getAlbumsByIds(libraryAlbumsIds, albums)

            if (libraryAlbums.isEmpty()){
                Box(modifier = Modifier.padding(20.dp, 40.dp)){
                    Snackbar(showMessage = "Library is Empty")
                }

            }

            Spacer(modifier = Modifier.height(10.dp))
                repeat(libraryAlbums.size){ album ->


                        Row(horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 10.dp)
                        ){
                            GlideImage(modifier = Modifier
                                .size(57.dp),
                                model = libraryAlbums[album].coverUri,
                                contentScale = ContentScale.Crop,
                                contentDescription = "")
                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Text(text = libraryAlbums[album].name, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold )
                                Text(text = libraryAlbums[album].artists, color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                }

            Spacer(modifier = Modifier.height(130.dp))
        }
}