package com.music.stream.neptune.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.music.stream.neptune.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AlbumScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(16.dp, 0.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.navigate("home")
                        },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "",
                        tint = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(text = "Album name")
                })
        }
    ){
        Column(modifier = Modifier.background(Color.Gray).verticalScroll(rememberScrollState())) {

            Spacer(modifier = Modifier.padding(30.dp))
            Image(
                modifier = Modifier
                    .height(385.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(id = R.drawable.album),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(25.dp))
            repeat(10) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(200.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(60.dp),
                            painter = painterResource(id = R.drawable.album),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(
                                text = "Album name",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Song singer Name",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.MoreVert, tint = Color.Gray, contentDescription = ""
                    )
                }
            }
        }

    }
    }
