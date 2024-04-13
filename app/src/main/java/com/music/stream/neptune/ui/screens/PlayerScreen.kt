package com.music.stream.neptune.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.music.stream.neptune.R
import com.music.stream.neptune.ui.navigation.Routes

@Composable
fun PlayerScreen(navController: NavController) {
    var sliderPosition = remember{
        mutableStateOf(0f)
    }
        Column(modifier = Modifier
            .fillMaxSize()) {
            PlayerTopBar(navController)
            Spacer(modifier = Modifier.padding(16.dp))
            Image(
                modifier = Modifier
                    .height(385.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                ,painter = painterResource(id = R.drawable.album),
                contentScale = ContentScale.Crop,
                contentDescription = "")
            Spacer(modifier = Modifier.padding(30.dp))
            PlayerInfo()
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
        Text(text = "PLAYING SONG", color = Color.White)
        Icon(painter = painterResource(id = R.drawable.ic_dots), tint = Color.White, contentDescription = "")
    }
}

@Composable
fun PlayerInfo() {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)){
        Column {
            Text(text = "Album Name", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "singer name", color = Color.White, fontSize = 15.sp)
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.width(90.dp)) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.ic_minus),
                tint = Color.White,
                contentDescription = "")
            Icon(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.ic_add),
                tint = Color.White,
                contentDescription = "")
        }
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
                .size(25.dp),
            painter = painterResource(id = R.drawable.ic_devices),
            tint = Color.White,
            contentDescription = "")
        Icon(
            modifier = Modifier
                .size(18.dp),
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