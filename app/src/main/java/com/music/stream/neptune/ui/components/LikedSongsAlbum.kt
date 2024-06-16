package com.music.stream.neptune.ui.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
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
import com.music.stream.neptune.data.entity.AlbumsModel
import com.music.stream.neptune.data.entity.SongsModel
import com.music.stream.neptune.data.pref.getLikedSongIds
import com.music.stream.neptune.data.pref.getSongsByIds
import com.music.stream.neptune.di.Palette
import com.music.stream.neptune.di.SongPlayer
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.viewmodel.AlbumViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun LikedSongsScreen(
    albums: List<AlbumsModel>,
    songs: List<SongsModel>,
    navController: NavController,
    context: Context
) {
    val albumViewModel : AlbumViewModel =  hiltViewModel()
    val likesSongIds = getLikedSongIds(context)
    val album = albums.filter { it.name == "Liked Songs" }
    val likedSongs = getSongsByIds(likesSongIds, songs)

    var dominentColor by remember {
        mutableStateOf(Color(AppBackground.toArgb()))
    }
    Palette().extractSecondColorFromCoverUrl(context = context, album[0].coverUri){ color ->
        dominentColor = color
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(16.dp, 0.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
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
                    .padding(20.dp, 10.dp),
                    text = "Liked Songs",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold)


                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(25.dp, 0.dp)
                ){

                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .width(75.dp)
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .height(42.dp)
                                .width(32.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .border(2.dp, Color.Gray, RectangleShape)
                                .padding(5.dp),
                            model = album[0].coverUri,
                            failure = placeholder(R.drawable.placeholder),
                            //loading = placeholder(R.drawable.album),
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                        )
                        Icon(
                            modifier = Modifier
                                .size(23.dp),
                            painter = painterResource(id = R.drawable.ic_add),
                            tint = Color.White,
                            contentDescription = "")
                    }


                    if(!albumViewModel.currentSongPlayingState.value && likedSongs.isNotEmpty()){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(Color.White)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    SongPlayer.playSong(likedSongs[0].url, context)
                                    albumViewModel.updateSongState(
                                        likedSongs[0].coverUri,
                                        likedSongs[0].title,
                                        likedSongs[0].singer,
                                        true,
                                        likedSongs[0].id,
                                        0,
                                        "Liked Songs"
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

//            Spacer(modifier = Modifier.padding(25.dp))

            if(likedSongs.isNotEmpty()){
                repeat(likedSongs.size) {song ->
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
                                SongPlayer.playSong(likedSongs[song].url, context)
                                albumViewModel.updateSongState(
                                    likedSongs[song].coverUri,
                                    likedSongs[song].title,
                                    likedSongs[song].singer,
                                    true,
                                    likedSongs[song].id,
                                    song,
                                    "Liked Songs"
                                )
                            }
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.width(200.dp)
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                                    .size(50.dp),
                                model = likedSongs[song].coverUri,
                                contentScale = ContentScale.Crop,
                                failure = placeholder(R.drawable.placeholder),
                                loading = placeholder(R.drawable.placeholder),
                                contentDescription = ""
                            )
                            Column {
                                Text(
                                    text = likedSongs[song].title,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = likedSongs[song].singer,
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.MoreVert, tint = Color.Gray, contentDescription = ""
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(80.dp))
        }

    }

}