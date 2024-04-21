package com.music.stream.neptune.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.music.stream.neptune.R
import com.music.stream.neptune.di.songPlayer
import com.music.stream.neptune.ui.theme.AppBackground
import com.music.stream.neptune.ui.theme.GridBackground

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
@Preview
@Composable
fun MiniPlayer() {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(GridBackground.toArgb()))
            .padding(10.dp, 5.dp)
            .clickable {

            }
    ){

        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(280.dp)) {
            GlideImage(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    .size(45.dp)
                    .clip(RoundedCornerShape(6.dp))
                ,
                model = R.drawable.album,
                contentScale = ContentScale.Crop,
                failure = placeholder(R.drawable.placeholder),
                loading = placeholder(R.drawable.placeholder),
                contentDescription = ""
            )
            Column {
                Text(text = "song", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium )
                Text(text = "singer", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_playing),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    songPlayer.pause()
                }
            )
    }
}