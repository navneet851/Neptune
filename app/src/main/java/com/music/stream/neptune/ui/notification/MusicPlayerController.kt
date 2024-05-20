package com.music.stream.neptune.ui.notification

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.music.stream.neptune.R
import com.music.stream.neptune.di.SongPlayer
import com.music.stream.neptune.ui.screens.CustomSlider

@OptIn(ExperimentalGlideComposeApi::class)
@Preview
@Composable
fun MusicPlayerController() {
    //val notificationViewModel : PlayerViewModel = hiltViewModel()



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ){
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Neptune",
                fontSize = 10.sp

            )
            Text(
                text = "Song Name",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Singer name",
                fontSize = 14.sp,
            )

            NotificationPlayer(songPlayingState = false, context = LocalContext.current)

            CustomSlider(
                value = 0f,
                onValueChange = { newValue ->
                    SongPlayer.seekTo(newValue.toLong())
                },
                valueRange = 0f..1f,
                steps = 0,
                modifier = Modifier
                    .fillMaxWidth(),
                   // .padding(16.dp, 20.dp, 16.dp, 0.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.LightGray
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp, 0.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "0:00",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "0:00",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Image(
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(10.dp))
                .zIndex(-2f),
            painter = painterResource(id = R.drawable.logo), // Replace with a placeholder image
            contentScale = ContentScale.Crop,
            contentDescription = "Background Image"
        )

//        GlideImage(
//            modifier = Modifier
//                .size(170.dp)
//                .align(Alignment.BottomEnd)
//                .border(BorderStroke(1.dp, Color.Black))
//                .background(Color.Green)
//                .zIndex(-2f),
//            model = R.drawable.album,
//            contentDescription = " ",
//
//            )
        Box(
            modifier = Modifier
                .height(170.dp)
                .width(190.dp)
                .align(Alignment.BottomEnd)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.White,
                            Color.Transparent,
                            Color.Transparent
                        ),
                        startX = 100f
                    )
                )
                .zIndex(-2f)
        )

    }
}

@Composable
fun NotificationPlayer(
    songPlayingState: Boolean,
    context: Context
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(180.dp)
    ) {

        Icon(
            modifier = Modifier
                .size(23.dp),
            painter = painterResource(id = R.drawable.ic_add),
            tint = Color.Black,
            contentDescription = "")

        Icon(
            modifier = Modifier
                .size(25.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    //playerViewModel.playPreviousSong(queueSongs, context)
                }
            ,
            tint = Color.Black,
            painter = painterResource(id = R.drawable.ic_player_back),
            contentDescription = "")

        Icon(
            modifier = Modifier
                .size(35.dp)

            ,
            tint = Color.Black,
            painter = if (songPlayingState)
                painterResource(id = R.drawable.ic_playing)
            else
                painterResource(id = R.drawable.play_svgrepo_com)
            ,
            contentDescription = "")

        Icon(
            modifier = Modifier
                .size(25.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    //playerViewModel.playNextSongs(queueSongs, context)
                }
            ,
            tint = Color.Black,
            painter = painterResource(id = R.drawable.ic_player_skip),
            contentDescription = "")



    }
}