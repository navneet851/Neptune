package com.music.stream.neptune.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.music.stream.neptune.R
import com.music.stream.neptune.data.entity.AlbumsModel
import com.music.stream.neptune.data.entity.ArtistsModel
import com.music.stream.neptune.ui.theme.appbackground
import com.music.stream.neptune.ui.viewmodel.HomeViewModel
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController){

    val homeViewModel : HomeViewModel = hiltViewModel()
    val albums by homeViewModel.albums.collectAsState()
    val artists by homeViewModel.artists.collectAsState()
    val songs by homeViewModel.songs.collectAsState()



    //Log.d("checker", albums.toString())

    Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(appbackground.toArgb()))
    ){

            GreetingSection()
            //ChipSection(chip = listOf(" All ", "Music", "Podcasts"))
            HomePlaylistGrid(navController, albums)

            //HomePlaylistGrid1(albums)
            HomeAlbums(albums = albums)
            HomeRecentlyPlayed(navController, albums = listOf("karan aujla", "diljit", "fudfu", "frref", "frrf"))
            ImageCard(image = R.drawable.album, contentDescription = "album", artists = artists)
        }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GreetingSection(name : String = "User") {
    val currentHour = LocalTime.now().hour
    val greeting = when {
        currentHour < 12 -> "Good Morning"
        currentHour < 17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "$greeting, $name",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
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

//@Composable
//fun ChipSection(
//    chip : List<String>
//) {
//    var selectedChip by remember {
//        mutableStateOf(0)
//    }
//    LazyRow{
//        items(chip.size){
//            Box(contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .padding(15.dp, 0.dp, 0.dp, 0.dp)
//                    .clickable {
//                        selectedChip = it
//                    }
//                    .clip(RoundedCornerShape(50.dp))
//                    .background(
//                        if (selectedChip == it) Color.Green
//                        else Color.Gray
//                    )
//                    .padding(10.dp, 5.dp)
//
//            ){
//                Text(text = chip[it], color = Color.White)
//            }
//        }
//    }
//}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomePlaylistGrid(navController: NavController, albums: List<AlbumsModel>) {
    val chunkedAlbums = albums.chunked(2)
    Log.d("giveme", chunkedAlbums.toString())
    Column(
        modifier = Modifier
            .padding(0.dp, 10.dp)
    ){
        repeat(chunkedAlbums.size){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(15.dp, 5.dp, 7.dp, 0.dp)
                    .fillMaxWidth()
            )
            {
                repeat(chunkedAlbums[it].size){ album ->
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.DarkGray)
                            .width(180.dp)
                            .clickable {
                                navController.navigate("albums")
                            }
                    ) {
                        GlideImage(modifier = Modifier
                            .size(55.dp),
                            contentScale = ContentScale.Crop,
                            model = chunkedAlbums[it][album].coverUri,
                            contentDescription = "Profile")
                        Text(modifier = Modifier.padding(5.dp),
                            text = chunkedAlbums[it][album].name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }

            }
        }
    }


}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomePlaylistGrid1( albums : List<AlbumsModel>) {
    val chunkedAlbums = albums.chunked(2)
    LazyColumn {
        items(chunkedAlbums.size) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.DarkGray)
                    .width(180.dp)
                    .clickable {
                        //navController.navigate("albums")
                    }
            ) {
                for (album in chunkedAlbums[it]) {
                    GlideImage(
                        modifier = Modifier
                            .size(55.dp),
                        contentScale = ContentScale.Crop,
                        model = R.drawable.album,
                        contentDescription = "Profile"
                    )
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "dfdvf",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                }
            }

        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeAlbums(
    albums : List<AlbumsModel>
) {
    Text(modifier = Modifier
        .padding(20.dp, 10.dp, 0.dp, 0.dp),
        text = "Albums",
        color = Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold)
        LazyRow(modifier = Modifier.padding(6.dp)){
            items(albums.size){album ->
                Box(modifier = Modifier
                    .padding(10.dp)
                    .width(150.dp)
                    .height(200.dp)
            ){
                Column(horizontalAlignment = Alignment.Start) {



                    GlideImage(modifier = Modifier
                        .size(150.dp)
                        .background(Color.Green),
                        contentScale = ContentScale.Crop,
                        model = albums[album].coverUri,
                        contentDescription = "Albums")
                    Text(modifier = Modifier.padding(2.dp),
                        text = albums[album].name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold)
                    Text(modifier = Modifier.padding(2.dp),
                        text = albums[album].artists,
                        color = Color.White)
                }

            }
        }
    }
}

@Composable
fun HomeRecentlyPlayed(
    navController: NavController,
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
                .clickable {
                    navController.navigate("playsong")
                }
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
    image: Int,
    contentDescription: String,
    artists : List<ArtistsModel>,
    modifier: Modifier = Modifier
) {
    Column {
        repeat(artists.size) {
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
                        painter = painterResource(id = image),
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
                            text = artists[it].name,
                            style = TextStyle(color = Color.White, fontSize = 16.sp),
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}





















