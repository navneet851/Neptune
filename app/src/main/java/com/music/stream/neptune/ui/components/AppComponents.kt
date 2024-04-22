package com.music.stream.neptune.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.music.stream.neptune.di.Palette
import com.music.stream.neptune.di.songPlayer
import com.music.stream.neptune.ui.navigation.Routes
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.viewmodel.PlayerViewModel

@Composable
fun Loader() {
    Column(Modifier
        .background(Color(AppBackground.toArgb())),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(45.dp),
            color = Color(0xFF4A4AC4)
        )
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MiniPlayer(navController: NavController) {
    val miniPlayerViewModel : PlayerViewModel = hiltViewModel()
    val songTitle = miniPlayerViewModel.currentSongTitle.value
    val songSinger = miniPlayerViewModel.currentSongSinger.value
    val songCoverUri = miniPlayerViewModel.currentSongCoverUri.value
    val songPlayingState = miniPlayerViewModel.currentSongPlayingState.value

    val context = LocalContext.current

    var darkVibrantColor by remember {
        mutableStateOf(Color(AppBackground.toArgb()))
    }
    Palette().extractDominantColorFromImageUrl(context = context, songCoverUri){ color ->
        darkVibrantColor = color
    }
    Log.d("checkplayermini", songTitle)


    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp, 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(darkVibrantColor)
            .padding(10.dp, 5.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navController.navigate(Routes.Player.route)
            }
    ){

        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(280.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    .size(45.dp)
                    .clip(RoundedCornerShape(6.dp))
                ,
                model = songCoverUri,
                contentScale = ContentScale.Crop,
                failure = placeholder(R.drawable.placeholder),
                loading = placeholder(R.drawable.placeholder),
                contentDescription = ""
            )
            Column {
                Text(text = songTitle, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium )
                Text(text = songSinger, color = Color.LightGray, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }

        Icon(
            painter = if (songPlayingState)
                painterResource(id = R.drawable.ic_playing)
            else
                painterResource(id = R.drawable.ic_paused)
                ,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                //.size(28.dp)
                .padding(8.dp, 0.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (songPlayingState){
                        songPlayer.pause()
                        miniPlayerViewModel.updateSongState(songCoverUri, songTitle, songSinger, false)
                    }
                    else{
                        songPlayer.play()
                        miniPlayerViewModel.updateSongState(songCoverUri, songTitle, songSinger, true)
                    }
                }
            )
    }
}