package com.music.stream.neptune.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.music.stream.neptune.R

@Preview
@Composable
fun HomeScreen() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            GreetingSection()
            ChipSection(chip = listOf(" All ", "Music", "Podcasts"))
            HomePlaylistGrid()
            HomeAlbums(albums = listOf("karan aujla", "diljit", "fudfu", "frref", "frrf"))
            HomeRecentlyPlayed(albums = listOf("karan aujla", "diljit", "fudfu", "frref", "frrf"))
            ImageCard(Image = R.drawable.album, contentDescription = "album", title = "Amlum : karan Aujla")
        }
}

@Composable
fun GreetingSection(name : String = "User") {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Good Morning, $name",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
                )
            Text(
                text = "Have a Nice Day",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
                )
        }
        Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile", tint = Color.White)
    }
}

@Composable
fun ChipSection(
    chip : List<String>
) {
    var selectedChip by remember {
        mutableStateOf(0)
    }
    LazyRow{
        items(chip.size){
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 0.dp)
                    .clickable {
                        selectedChip = it
                    }
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        if (selectedChip == it) Color.Green
                        else Color.Gray
                    )
                    .padding(10.dp, 5.dp)

            ){
                Text(text = chip[it], color = Color.White)
            }
        }
    }
}

@Composable
fun HomePlaylistGrid() {
    Column(
        modifier = Modifier
            .padding(0.dp, 10.dp)
    ){
        repeat(4){
            LazyRow(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(15.dp, 5.dp, 7.dp, 0.dp)
                    .fillMaxWidth()
            )
            {
                items(2){
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.DarkGray)
                            .width(180.dp)
                    ) {
                        Image(modifier = Modifier
                            .size(55.dp),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(id = R.drawable.album), contentDescription = "Profile")
                        Text(modifier = Modifier.padding(5.dp),
                            text = "Liked Songs",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )

                    }
                }

            }
        }
    }


}

@Composable
fun HomeAlbums(
    albums : List<String>
) {
    Text(modifier = Modifier
        .padding(20.dp, 10.dp, 0.dp, 0.dp),
        text = "Albums",
        color = Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold)
        LazyRow(modifier = Modifier.padding(6.dp)){
            items(albums.size){
                Box(modifier = Modifier
                    .padding(10.dp)
                    .width(150.dp)
                    .height(200.dp)
            ){
                Column(horizontalAlignment = Alignment.Start) {
                    Image(modifier = Modifier
                        .size(150.dp)
                        .background(Color.Green),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.album),
                        contentDescription = "Albums")
                    Text(modifier = Modifier.padding(2.dp),
                        text = "Album name",
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                    Text(modifier = Modifier.padding(2.dp),
                        text = "Artist name",
                        color = Color.White)
                }

            }
        }
    }
}

@Composable
fun HomeRecentlyPlayed(
    albums : List<String>
) {
    Text(modifier = Modifier
        .padding(20.dp, 10.dp, 0.dp, 0.dp),
        text = "Recently Played",
        color = Color.White,
        fontSize = 23.sp,
        fontWeight = FontWeight.Bold)
    LazyRow(modifier = Modifier.padding(6.dp)){
        items(albums.size){
            Box(modifier = Modifier
                .padding(10.dp)
                .width(130.dp)
                .height(200.dp)
            ){
                Column(horizontalAlignment = Alignment.Start) {
                    Image(modifier = Modifier
                        .size(120.dp)
                        .background(Color.Green),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.album),
                        contentDescription = "Albums")
                    Text(modifier = Modifier.padding(2.dp),
                        text = "Album name",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp)
                }

            }
        }
    }
}


@Composable
fun ImageCard(
    Image: Int,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column() {
        repeat(2) {
            Card(
                shape = RoundedCornerShape(25.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = Image),
                        contentDescription = contentDescription,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = 250f
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(color = Color.White, fontSize = 16.sp),
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}





















