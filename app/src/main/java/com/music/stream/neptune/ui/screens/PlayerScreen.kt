package com.music.stream.neptune.ui.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.music.stream.neptune.R
import com.music.stream.neptune.di.Palette
import com.music.stream.neptune.di.SongPlayer
import com.music.stream.neptune.ui.navigation.Routes
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.viewmodel.PlayerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerScreen(navController: NavController, id: String) {
    val playerViewModel : PlayerViewModel = hiltViewModel()
    val songTitle = playerViewModel.currentSongTitle.value
    val songSinger = playerViewModel.currentSongSinger.value
    val songCoverUri = playerViewModel.currentSongCoverUri.value
    val songPlayingState = playerViewModel.currentSongPlayingState.value

    val songDuration by remember { mutableStateOf(maxOf(0f, SongPlayer.getDuration().toFloat())) }
    var songProgress by remember { mutableStateOf(maxOf(0f, SongPlayer.getCurrentPosition().toFloat())) }
    var songDurationText by remember { mutableStateOf("0") }
    var songProgressText by remember { mutableStateOf("") }


    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.Main) {
            while (true) {
                songProgress = SongPlayer.getCurrentPosition().toFloat()
                songProgressText = playerViewModel.formatDuration(songProgress.toLong())
                songDurationText = playerViewModel.formatDuration(SongPlayer.getDuration())
                delay(100L) // update every second
            }
        }
    }

    Log.d("checkplayer", songTitle)

    //playerViewModel.updateSongState(songCoverUri, songTitle, songSinger, songPlayingState)

    val context = LocalContext.current

    var dominentColor by remember {
        mutableStateOf(Color(AppBackground.toArgb()))
    }
    Palette().extractMutedColorFromCoverUrl(context = context, songCoverUri){ color ->
        dominentColor = color
    }

        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        dominentColor,
                        Color.Black
                    ),
                    startY = 100f
                )
            )
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

            CustomSlider(
                value = songProgress,
                onValueChange = { newValue ->
                    SongPlayer.seekTo(newValue.toLong())
                },
                valueRange = 0f..songDuration,
                steps = 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 20.dp, 16.dp, 0.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 0.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = songProgressText,
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = songDurationText,
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }


            Spacer(modifier = Modifier.padding(5.dp))
            PlayerFull(songPlayingState, playerViewModel)
            //PlayerEndInfo()
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
            modifier = Modifier
                .width(270.dp)
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
                    color = Color.Gray,
                    fontSize = 13.sp,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    colors: SliderColors = SliderDefaults.colors(),
) {
    Box(modifier = modifier.height(10.dp)) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            colors = colors,
            thumb = {
                SliderDefaults.Thumb( //androidx.compose.material3.SliderDefaults
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier.align(Alignment.Center),
                    colors = colors,
                    thumbSize = DpSize(9.dp, 9.dp)
                )
            }
        )
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
fun PlayerFull(songPlayingState: Boolean, playerViewModel: PlayerViewModel) {
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
                    .size(30.dp)
                    .clickable {
                        if (songPlayingState){
                            SongPlayer.pause()
                            playerViewModel.updateSongState(
                                playerViewModel.currentSongCoverUri.value,
                                playerViewModel.currentSongTitle.value,
                                playerViewModel.currentSongSinger.value,
                                false)
                        }
                        else{
                            SongPlayer.play()
                            playerViewModel.updateSongState(
                                playerViewModel.currentSongCoverUri.value,
                                playerViewModel.currentSongTitle.value,
                                playerViewModel.currentSongSinger.value,
                                true)
                        }
                    }
                ,
                tint = Color.Black,
                painter = if (songPlayingState)
                    painterResource(id = R.drawable.ic_playing)
                else
                    painterResource(id = R.drawable.play_svgrepo_com)
                ,
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