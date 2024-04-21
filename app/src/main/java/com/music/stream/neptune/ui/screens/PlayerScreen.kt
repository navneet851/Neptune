package com.music.stream.neptune.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.music.stream.neptune.R
import com.music.stream.neptune.ui.navigation.Routes
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.viewmodel.PlayerViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerScreen(navController: NavController) {
    val playerViewModel : PlayerViewModel = hiltViewModel()
    val songTitle = playerViewModel.currentSongTitle.value
    val songSinger = playerViewModel.currentSongSinger.value
    val songCoverUri = playerViewModel.currentSongCoverUri.value
    val songPlayingState = playerViewModel.currentSongPlayingState.value

    Log.d("checkplayer", songTitle)

    playerViewModel.updateSongState(songCoverUri, songTitle, songSinger, songPlayingState)


    var sliderPosition = remember{
        mutableStateOf(0f)
    }
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(AppBackground.toArgb()))
            .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerTopBar(navController)
            Spacer(modifier = Modifier.padding(16.dp))
            GlideImage(
                modifier = Modifier
                    .size(370.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                ,
                model = songCoverUri,
                contentScale = ContentScale.Crop,
                contentDescription = "")
            Spacer(modifier = Modifier.padding(30.dp))
            PlayerInfo(songTitle, songSinger)
            Slider(
                modifier = Modifier
                    .height(20.dp)
                    .padding(20.dp),
                value = sliderPosition.value,
                onValueChange = {
                    sliderPosition.value = it
                },
            )
            Spacer(modifier = Modifier.padding(16.dp))
            PlayerFull()
            PlayerEndInfo()
        }
    }
@Composable
fun PlayerTopBar(navController: NavController) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(modifier = Modifier
            .clickable {
                       navController.navigate(Routes.Home.route)
            },
            painter = painterResource(id = R.drawable.ic_down),
            tint = Color.White,
            contentDescription = "")

        Text(
            text = "PLAYING SONG",
            color = Color.White,
            fontSize = 11.sp
        )
        Icon(
            imageVector = Icons.Default.MoreVert,
            tint = Color.White,
            modifier = Modifier.size(23.dp),
            contentDescription = "")
    }
}

@Composable
fun PlayerInfo(songTitle: String, songSinger: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 10.dp)
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
                    text = songTitle,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = songSinger,
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Icon(
            modifier = Modifier
                .size(26.dp),
            painter = painterResource(id = R.drawable.ic_add),
            tint = Color.White,
            contentDescription = "")
    }
}

@Composable
fun PlayerEndInfo() {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)){
        Icon(
            modifier = Modifier
                .size(22.dp),
            painter = painterResource(id = R.drawable.ic_devices),
            tint = Color.White,
            contentDescription = "")
        Icon(
            modifier = Modifier
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_share),
            tint = Color.White,
            contentDescription = "")
    }
}

@Composable
fun PlayerFull() {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(25.dp),
            tint = Color.Gray,
            painter = painterResource(id = R.drawable.ic_player_shuffle),
            contentDescription = "")
        Icon(
            modifier = Modifier
                .size(35.dp),
            tint = Color.White,
            painter = painterResource(id = R.drawable.ic_player_back),
            contentDescription = "")
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(65.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                tint = Color.Black,
                painter = painterResource(id = R.drawable.ic_playing),
                contentDescription = "")
        }

        Icon(
            modifier = Modifier
                .size(35.dp),
            tint = Color.White,
            painter = painterResource(id = R.drawable.ic_player_skip),
            contentDescription = "")
        Icon(
            modifier = Modifier
                .size(20.dp),
            tint = Color.Gray,
            painter = painterResource(id = R.drawable.ic_repeat),
            contentDescription = "")
    }
}