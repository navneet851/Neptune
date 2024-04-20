package com.music.stream.neptune.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.music.stream.neptune.data.api.Response
import com.music.stream.neptune.data.entity.ArtistsModel
import com.music.stream.neptune.data.entity.SongsModel
import com.music.stream.neptune.ui.components.Loader
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.viewmodel.ArtistViewModel


@Composable
fun ArtistScreen(navController: NavController, artistName: String) {


    val artistViewModel : ArtistViewModel = hiltViewModel()
    val songs by artistViewModel.songs.collectAsState()
    val artists by artistViewModel.artists.collectAsState()

    Log.d("checku", artistName.toString())
    Log.d("checkun", songs.toString())
    Log.d("checku", artists.toString())

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(AppBackground.toArgb()))
    ) {
        when(songs){
            is Response.Loading -> {
                Log.d("homeMain", "loading-artists-songs..")
                Loader()
            }

            is Response.Success -> {
                val songsResponse = (songs as Response.Success).data
                val artistsResponse = (artists as Response.Success).data
                Log.d("homeMain", "Success-artists-songs. ${artistsResponse.toString()}")
                SumUpArtistScreen(navController = navController, songsResponse.shuffled(), artistsResponse, artistName)
            }

            is Response.Error -> {
                Log.d("homeMain", "Error!!")
            }
        }
    }

}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun SumUpArtistScreen(
    navController: NavController,
    songs: List<SongsModel>,
    artists: List<ArtistsModel>,
    artistName: String
) {

    val artistSongs = songs.filter {
        artistName == it.singer
    }
    val artist = artists.filter{
        artistName == it.name
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(16.dp, 0.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.navigate("home")
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
                            colors = listOf(Color.White, Color(AppBackground.toArgb())),
                            startY = -1000f,

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
                        modifier = Modifier.size(225.dp),
                        model = artist[0].coverUri,
                        failure = placeholder(R.drawable.placeholder),
                        //loading = placeholder(R.drawable.album),
                        //contentScale = ContentScale.Crop,
                        contentDescription = "",
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Text(modifier = Modifier
                    .padding(20.dp, 5.dp, 0.dp, 0.dp),
                    text = artistName,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium)
                Text(modifier = Modifier
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    text = "Made for You",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium)
//                Text(modifier = Modifier
//                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
//                    text = "Album : ${artist[0].time}",
//                    color = Color.Gray,
//                    fontSize = 11.sp,
//                    fontWeight = FontWeight.Medium
//                )

                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)
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
                            model = artist[0].coverUri,
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

                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            tint = Color.Black,
                            painter = painterResource(id = R.drawable.ic_playing),
                            contentDescription = "")
                    }
                }

            }

//            Spacer(modifier = Modifier.padding(25.dp))


            repeat(artistSongs.size) { song ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 8.dp)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(280.dp)
                    ) {
                        GlideImage(
                            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp).size(50.dp),
                            model = artistSongs[song].coverUri,
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                        Column {
                            Text(
                                text = artistSongs[song].title,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = artistSongs[song].singer,
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
            Spacer(modifier = Modifier.padding(80.dp))
        }

    }
}
