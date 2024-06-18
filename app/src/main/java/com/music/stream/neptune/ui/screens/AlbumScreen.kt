package com.music.stream.neptune.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.music.stream.neptune.R
import com.music.stream.neptune.data.api.Response
import com.music.stream.neptune.data.entity.AlbumsModel
import com.music.stream.neptune.data.entity.SongsModel
import com.music.stream.neptune.data.preferences.addLikedAlbumId
import com.music.stream.neptune.data.preferences.addLikedSongId
import com.music.stream.neptune.data.preferences.isAlbumLiked
import com.music.stream.neptune.data.preferences.isSongLiked
import com.music.stream.neptune.data.preferences.removeLikedAlbumId
import com.music.stream.neptune.data.preferences.removeLikedSongId
import com.music.stream.neptune.di.Palette
import com.music.stream.neptune.di.SongPlayer
import com.music.stream.neptune.ui.components.LikedSongsScreen
import com.music.stream.neptune.ui.components.Loader
import com.music.stream.neptune.ui.components.Snackbar
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.theme.AppPalette
import com.music.stream.neptune.ui.viewmodel.AlbumViewModel
import kotlinx.coroutines.delay


@Composable
fun AlbumScreen(navController: NavController, albumName: String) {


    val albumViewModel : AlbumViewModel = hiltViewModel()
    val songs by albumViewModel.songs.collectAsState()
    val albums by albumViewModel.albums.collectAsState()

    val context = LocalContext.current




    Log.d("check", albumName.toString())

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(AppBackground.toArgb()))
    ) {
        when(albums){
            is Response.Loading -> {
                Log.d("homeMain", "loading..-albums")
                Loader()
            }

            is Response.Success -> {
                val albumsResponse = (albums as Response.Success).data
                val songsResponse = (songs as Response.Success).data
                Log.d("homeMain", "Success..-albums")

                if (albumName == "Liked Songs"){
                    LikedSongsScreen(albumsResponse, songsResponse, navController, context)
                }
                else{
                    SumUpAlbumScreen(navController = navController,albumViewModel, albumsResponse, songsResponse, albumName, context)
                }
            }

            is Response.Error -> {
                Log.d("homeMain", "Error!!-albums")
            }

            else -> {
                Log.d("homeMain", "else")
            }
        }
    }

}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SumUpAlbumScreen(
    navController: NavController,
    albumViewModel: AlbumViewModel,
    albums: List<AlbumsModel>,
    songs: List<SongsModel>,
    albumName: String,
    context: Context
) {
    val songsByAlbum: Map<String, List<SongsModel>> = songs.groupBy { it.album }
    val albumSongs: List<SongsModel> = songsByAlbum[albumName]!!
   // Log.d("checks", songsInAlbum.toString())

//    val albumSongs = songs.filter {
//        albumName == it.album
//    }
    val albumByName : Map<String, List<AlbumsModel>> = albums.groupBy { it.name }
    val album : List<AlbumsModel> = albumByName[albumName]!! // current album
    var dominentColor by remember {
        mutableStateOf(Color(AppBackground.toArgb()))
    }
    Palette().extractSecondColorFromCoverUrl(context = context, album[0].coverUri){ color ->
        dominentColor = color
    }

    var isAlbumLiked by remember { mutableStateOf( isAlbumLiked(context, album[0].id.toString())) }

    var snackbarMessage by remember {
        mutableStateOf("")
    }
    var snackbarVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(snackbarVisible) {
        delay(1500)
        snackbarVisible = false
    }



    Log.d("color", dominentColor.toString())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(16.dp, 0.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.navigateUp()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(text = "")
                }
            )
        }
    ){


        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(AppBackground.toArgb()))
            .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(dominentColor, Color(AppBackground.toArgb())),
                            startY = -100f,

                            ),

                        )
                ,
                verticalArrangement = Arrangement.Center,
               // horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(25.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        modifier = Modifier.size(230.dp),
                        model = album[0].coverUri,
                        failure = placeholder(R.drawable.placeholder),
                        //loading = placeholder(R.drawable.album),
                        //contentScale = ContentScale.Crop,
                        contentDescription = "",
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Text(modifier = Modifier
                    .padding(20.dp, 5.dp, 0.dp, 0.dp),
                    text = albumName,
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold)
                Text(modifier = Modifier
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    text = albumSongs[0].singer,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium)
                Text(modifier = Modifier
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    text = "Album : ${album[0].time}",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(25.dp, 0.dp)
                ){

                    if (snackbarVisible){
                            Snackbar(showMessage = snackbarMessage)
                        }
                    else{
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(75.dp)
                        ) {

                            GlideImage(
                                modifier = Modifier
                                    .height(55.dp)
                                    .width(41.dp)
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                ,
                                model = album[0].coverUri,
                                failure = placeholder(R.drawable.placeholder),
                                //loading = placeholder(R.drawable.album),
                                contentScale = ContentScale.Crop,
                                contentDescription = "",
                            )
                            Icon(
                                modifier = Modifier
                                    .size(23.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        if (isAlbumLiked) {
                                            removeLikedAlbumId(context, album[0].id.toString())
                                            snackbarMessage = "Removed from Library"
                                        } else {
                                            addLikedAlbumId(context, album[0].id.toString())
                                            snackbarMessage = "Added to Library"
                                        }
                                        isAlbumLiked = isAlbumLiked(context, album[0].id.toString())
                                        snackbarVisible = true

                                    },
                                painter = if (isAlbumLiked){
                                    painterResource(id = R.drawable.added)
                                }
                                else{
                                    painterResource(id = R.drawable.ic_add)
                                }
                                ,
                                tint = if (isAlbumLiked){
                                    Color(AppPalette.toArgb())
                                }
                                else{
                                    Color.White
                                },
                                contentDescription = ""
                            )
                        }


                        if(!albumViewModel.currentSongPlayingState.value && albumSongs.isNotEmpty()){
                            androidx.compose.foundation.layout.Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(Color.White)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        SongPlayer.playSong(albumSongs[0].url, context)
                                        albumViewModel.updateSongState(
                                            albumSongs[0].coverUri,
                                            albumSongs[0].title,
                                            albumSongs[0].singer,
                                            true,
                                            albumSongs[0].id,
                                            0,
                                            albumName
                                        )
                                    }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(25.dp),
                                    tint = Color.Black,
                                    painter = painterResource(id = R.drawable.play_svgrepo_com),
                                    contentDescription = "")
                            }
                        }
                    }




                }

            }

//            Spacer(modifier = Modifier.padding(25.dp))

            if(albumSongs.isNotEmpty()){
                repeat(albumSongs.size) {song ->


                    var isLiked by remember {
                        mutableStateOf(isSongLiked(context, albumSongs[song].id.toString()))
                    }
                    val likeState = albumViewModel.likeState.value
                    LaunchedEffect(likeState){
                        isLiked = isSongLiked(context, albumSongs[song].id.toString())
                    }
                    val songId = albumSongs[song].id
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                SongPlayer.playSong(albumSongs[song].url, context)
                                albumViewModel.updateSongState(
                                    albumSongs[song].coverUri,
                                    albumSongs[song].title,
                                    albumSongs[song].singer,
                                    true,
                                    albumSongs[song].id,
                                    song,
                                    albumName
                                )
                            }
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.width(200.dp)
                        ) {
//                        GlideImage(
//                            modifier = Modifier.size(60.dp),
//                            model = albumSongs[song].coverUri,
//                            contentScale = ContentScale.Crop,
//                            contentDescription = ""
//                        )
                            Column {
                                Text(
                                    text = albumSongs[song].title,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = albumSongs[song].singer,
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    if (isLiked) {
                                        removeLikedSongId(context, songId.toString())
                                    } else {
                                        addLikedSongId(context, songId.toString())
                                    }
                                    isLiked = isSongLiked(context, songId.toString())
                                    albumViewModel.updateLikeState(!albumViewModel.likeState.value)

                                },
                            painter = if (isLiked){
                                painterResource(id = R.drawable.added)
                            }
                            else{
                                painterResource(id = R.drawable.ic_add)
                            }
                            ,
                            tint = if (isLiked){
                                Color.White
                            }else{
                                Color.Gray
                            },
                            contentDescription = ""
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(80.dp))
        }

    }
}
